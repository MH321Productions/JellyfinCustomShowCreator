package io.github.mh321Productions.jellyfinCustomShowCreator

import com.jthemedetecor.OsThemeDetector
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import java.awt.EventQueue
import javax.swing.SwingUtilities

fun main() {
    EventQueue.invokeLater {
        val detector = OsThemeDetector.getDetector()
        ThemeUtils.setTheme(detector.isDark)

        val frame = MainFrame()
        frame.isVisible = true

        detector.registerListener { isDark -> if (frame.useAutoTheme) SwingUtilities.invokeLater { ThemeUtils.setTheme(isDark) } }
    }
}