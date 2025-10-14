package io.github.mh321Productions.jellyfinCustomShowCreator.ui.dialogs

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import java.awt.Dimension
import javax.swing.JDialog

class AboutDialog(frame: MainFrame) : JDialog(frame, "About", true) {

    init {
        size = Dimension(600, 400)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setLocationRelativeTo(frame)

        isVisible = true
    }
}