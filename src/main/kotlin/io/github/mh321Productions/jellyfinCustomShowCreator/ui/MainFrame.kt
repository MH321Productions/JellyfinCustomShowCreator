package io.github.mh321Productions.jellyfinCustomShowCreator.ui

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.MainMenuBar
import java.awt.Dimension
import javax.swing.JFrame

class MainFrame : JFrame() {

    var useAutoTheme = true
        private set


    init {
        title = "Jellyfin Custom Show Creator"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1280, 720)
        isResizable = true
        setLocationRelativeTo(null)

        jMenuBar = MainMenuBar(this)
    }
}