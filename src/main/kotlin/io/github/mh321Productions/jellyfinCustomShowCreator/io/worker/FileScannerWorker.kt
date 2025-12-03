package io.github.mh321Productions.jellyfinCustomShowCreator.io.worker

import io.github.mh321Productions.jellyfinCustomShowCreator.data.CodecType
import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.data.VideoInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.future.await
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.core.impl.multiplatform.InputStream
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JProgressBar

class FileScannerWorker : Worker {
    companion object {
        private val extensions = arrayOf("mkv", "mp4", "webm", "avi")
        private val ffprobeArgs = arrayOf("ffprobe", "-v", "quiet", "-of", "json", "-show_format", "-show_streams")
        private val logger = LoggerFactory.getLogger("File Scanner")
        private val jsonDecoder = Json {ignoreUnknownKeys = true}
    }

    private var maxNum = -1
    private var currentIndex = 0

    private val title: String
        get() = "Scanning files $currentIndex/$maxNum"

    override suspend fun doWork(frame: MainFrame, progressBar: JProgressBar) {
        val videoFiles = frame.rootDir.listFiles { file -> file.extension in extensions }?.toList() ?: return

        maxNum = videoFiles.size

        progressBar.maximum = maxNum
        progressBar.isStringPainted = true

        videoFiles
            .asFlow()
            .mapNotNull { readFile(it, progressBar) }
            .mapNotNull { parseEpisode(frame, it) }
            .collect {
                frame.show.unassignedEpisodes.add(it)
                frame.updateData()
            }

    }

    private suspend fun readFile(file: File, progressBar: JProgressBar): VideoInfo? {
        logger.debug("Reading file ${file.name}")

        progressBar.value = ++currentIndex
        progressBar.string = title

        val process = ProcessBuilder(*ffprobeArgs, file.name)
            .directory(file.parentFile)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        val string = process.inputStream.bufferedReader().readText()
        val err = process.errorStream.bufferedReader().readText()

        process.onExit().await()

        if (process.exitValue() != 0) {
            logger.error("Couldn't scan '${file.name}':\n${err}")
            return null
        }

        delay(500)

        return try {
            jsonDecoder.decodeFromString(string)
        } catch (e: Exception) {
            logger.error("Couldn't scan '${file.name}'", e)
            null
        }
    }

    private suspend fun parseEpisode(frame: MainFrame, info: VideoInfo): EpisodeInfo {
        val filename = info.format.filename
        val title = info.format.tags?.title ?: filename

        val attachmentStream = info.streams.firstOrNull { it.type == CodecType.Attachment }
        val thumbnail = if (attachmentStream != null) EpisodeInfo.ATTACHMENT_THUMBNAIL else ""
        val decodedThumbnail = if (attachmentStream != null) extractAttachmentThumbnail(File(frame.rootDir, info.format.filename), attachmentStream.index) else null

        return EpisodeInfo(
            title = title,
            originalFilename = filename,
            thumbnail = thumbnail,
            decodedThumbnail = decodedThumbnail
        )
    }

    private suspend fun extractAttachmentThumbnail(file: File, streamIndex: Int): BufferedImage? {
        val dumpArgs = listOf("ffmpeg", "-dump_attachment:$streamIndex", "pipe:1", "-i", file.absolutePath, "-t", "0", "-f", "null", "null")
        val convertArgs = listOf("ffmpeg", "-v", "quiet", "-i", "pipe:0", "-f", "image2", "-vcodec", "png", "pipe:1")

        val dumpBuilder = ProcessBuilder(dumpArgs).directory(file.parentFile)
        val convertBuilder = ProcessBuilder(convertArgs)

        val processes = ProcessBuilder.startPipeline(listOf(dumpBuilder, convertBuilder))
        val dumpProcess = processes[0]
        val convertProcess = processes[1]

        // Dump Attachment
        val dumpErr = dumpProcess.errorStream.bufferedReader().readText()
        dumpProcess.onExit().await()

        if (dumpProcess.exitValue() != 0) {
            logger.error("Couldn't extract attached thumbnail from file '${file.name}'\n$dumpErr")
            return null
        }

        //Convert and decode image
        val img = decodeImage(convertProcess.inputStream, "Couldn't decode attached thumbnail from file '${file.name}'")
        val convertErr = convertProcess.errorStream.bufferedReader().readText()
        convertProcess.onExit().await()

        if (convertProcess.exitValue() != 0) {
            logger.error("Couldn't decode attached thumbnail from file '${file.name}'\n$convertErr")
            return null
        }

        println("Image decoded")

        return img
    }

    private fun decodeThumbnail(file: File) = decodeImage(file.inputStream(), "Couldn't decode file '${file.name}'")

    private fun decodeImage(input: InputStream, errorMsg: String): BufferedImage? {
        return try {
            ImageIO.read(input)
        } catch (e: Exception) {
            logger.error(errorMsg, e)
            null
        }
    }
}