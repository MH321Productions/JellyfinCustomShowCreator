package io.github.mh321Productions.jellyfinCustomShowCreator.io.worker

import io.github.mh321Productions.jellyfinCustomShowCreator.data.VideoInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.io.File
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

        val infos = videoFiles
            .mapNotNull { readFile(it, progressBar) }

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
}