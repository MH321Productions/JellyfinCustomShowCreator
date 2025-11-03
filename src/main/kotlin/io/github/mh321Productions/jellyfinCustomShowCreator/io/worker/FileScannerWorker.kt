package io.github.mh321Productions.jellyfinCustomShowCreator.io.worker

import io.github.mh321Productions.jellyfinCustomShowCreator.data.VideoInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.slf4j.LoggerFactory
import java.io.File

class FileScannerWorker : Worker {
    companion object {
        private val extensions = arrayOf("mkv", "mp4", "webm", "avi")
        private val ffprobeArgs = arrayOf("ffprobe", "-v", "quiet", "-of", "json", "-show_format", "-show_streams")
        private val logger = LoggerFactory.getLogger("File Scanner")
        private val jsonDecoder = Json {ignoreUnknownKeys = true}
    }

    override suspend fun doWork(frame: MainFrame) {
        val videoFiles = frame.rootDir.listFiles { file -> file.extension in extensions }?.toList() ?: return

        val infos = videoFiles
            .mapNotNull { readFile(it) }

        val json = Json { prettyPrint = true }
        infos.forEach { println(json.encodeToString(it)) }
    }

    private suspend fun readFile(file: File): VideoInfo? {
        println("Reading file ${file.name}")

        val process = ProcessBuilder(*ffprobeArgs, file.name)
            .directory(file.parentFile)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        val string = process.inputStream.bufferedReader().readText()

        process.onExit().await()

        println("ffprobe finished")

        if (process.exitValue() != 0) {
            logger.error("Couldn't scan '${file.name}':\n${process.errorStream.bufferedReader().readText()}")
            return null
        }

        return try {
            jsonDecoder.decodeFromString(string)
        } catch (e: Exception) {
            logger.error("Couldn't scan '${file.name}'", e)
            null
        }
    }
}