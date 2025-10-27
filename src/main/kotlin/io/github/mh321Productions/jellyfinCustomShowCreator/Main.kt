package io.github.mh321Productions.jellyfinCustomShowCreator

import com.jthemedetecor.OsThemeDetector
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import java.awt.EventQueue
import java.io.File
import javax.swing.JFileChooser
import javax.swing.SwingUtilities

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        val detector = OsThemeDetector.getDetector()
        ThemeUtils.setTheme(detector.isDark)

        val rootDir = loadCmdlineArgDir(args.firstOrNull()) ?: selectDir() ?: return@invokeLater

        val frame = MainFrame(rootDir)
        frame.isVisible = true

        detector.registerListener { isDark -> SwingUtilities.invokeLater { ThemeUtils.setTheme(isDark) } }
    }
}

private fun loadCmdlineArgDir(arg: String?) : File? {
    if (arg == null) return null

    val file = File(arg)
    if (!file.exists() || !file.isDirectory) return null

    return file
}

private fun selectDir() : File? {
    val fc = JFileChooser()
    fc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
    fc.dialogTitle = "Select a project directory"
    fc.isMultiSelectionEnabled = false

    if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return null

    return fc.selectedFile
}