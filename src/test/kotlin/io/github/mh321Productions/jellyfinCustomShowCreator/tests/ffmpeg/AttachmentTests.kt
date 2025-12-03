package io.github.mh321Productions.jellyfinCustomShowCreator.tests.ffmpeg

import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.Test

class AttachmentTests {

    companion object {
        const val WIDTH = 1280
        const val HEIGHT = 720
    }

    private val videoFile: File
        get() = File(AttachmentTests::class.java.getResource("/video.mkv")!!.toURI())

    @Test
    fun `Export and convert thumbnail via temp files`() = runTest {
        // Arrange
        val tempWebp = File.createTempFile("thumbnail", ".webp")
        tempWebp.deleteOnExit()

        val tempPng = File.createTempFile("thumbnail", ".png")
        tempPng.deleteOnExit()

        // Extract
        val dumpArgs = listOf("ffmpeg", "-y", "-v", "quiet", "-dump_attachment:1", tempWebp.absolutePath, "-i", videoFile.absolutePath, "-t", "0", "-f", "null", "null")

        val dumpProcess = ProcessBuilder(dumpArgs)
            .start()

        val dumpErr = dumpProcess.errorStream.bufferedReader().readText()

        dumpProcess.onExit().await()

        assert(dumpProcess.exitValue() == 0) { dumpErr }

        // Convert
        val convertArgs = listOf("ffmpeg", "-y", "-v", "quiet", "-i", tempWebp.absolutePath, "-f", "image2", "-vcodec", "png", tempPng.absolutePath)
        val convertProcess = ProcessBuilder(convertArgs)
            .start()

        val convertErr = convertProcess.errorStream.bufferedReader().readText()

        convertProcess.onExit().await()

        assert(convertProcess.exitValue() == 0) { convertErr }

        val exportedImage = ImageIO.read(tempPng)
        assert(exportedImage != null)
        assert(exportedImage.width == WIDTH && exportedImage.height == HEIGHT)
    }

    @Test
    fun `Export and convert thumbnail via piping`() = runTest {
        // Arrange
        val dumpArgs = listOf("ffmpeg", "-v", "quiet", "-dump_attachment:1", "pipe:1", "-i", videoFile.absolutePath, "-t", "0", "-f", "null", "null")
        val convertArgs = listOf("ffmpeg", "-v", "quiet", "-i", "pipe:0", "-f", "image2", "-vcodec", "png", "pipe:1")

        val dumpBuilder = ProcessBuilder(dumpArgs)
        val convertBuilder = ProcessBuilder(convertArgs)

        // Act
        val processes = ProcessBuilder.startPipeline(listOf(dumpBuilder, convertBuilder))
        val dumpProcess = processes[0]
        val convertProcess = processes[1]

        dumpProcess.onExit().await()

        val decodedImage = ImageIO.read(convertProcess.inputStream)

        convertProcess.onExit().await()

        // Assert
        assert(decodedImage != null)
        assert(decodedImage.width == WIDTH && decodedImage.height == HEIGHT)
    }
}