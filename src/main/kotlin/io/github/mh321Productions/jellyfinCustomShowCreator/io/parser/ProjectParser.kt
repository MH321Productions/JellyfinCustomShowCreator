package io.github.mh321Productions.jellyfinCustomShowCreator.io.parser

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.io.File

object ProjectParser {
    private const val SHOW_FILE = "project.json"

    private val logger = LoggerFactory.getLogger("Project Parser")

    fun parseProject(rootDir: File): ShowInfo {
        val file = File(rootDir, SHOW_FILE)
        if (!file.exists()) return ShowInfo()

        val info = try {
            Json.decodeFromString(file.readText(Charsets.UTF_8))
        } catch (e: Exception) {
            logger.error("Couldn't parse project file, an empty file is used instead", e)
            ShowInfo()
        }

        info.seasons.sortBy { it.number }

        return info
    }

    fun saveProject(rootDir: File, showInfo: ShowInfo): Boolean {
        val showFile = File(rootDir, SHOW_FILE)
        val json = Json { prettyPrint = true }
        val encoded = json.encodeToString(showInfo)

        return try {
            showFile.writeText(encoded, Charsets.UTF_8)
            true
        } catch (e: Exception) {
            logger.error("Couldn't save project file", e)
            false
        }
    }
}