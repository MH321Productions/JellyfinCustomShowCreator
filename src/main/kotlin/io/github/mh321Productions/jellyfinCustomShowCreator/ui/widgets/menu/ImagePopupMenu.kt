package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images.ImagePanel
import javax.swing.JMenuItem
import javax.swing.JPopupMenu

class ImagePopupMenu(panel: ImagePanel) : JPopupMenu() {

    val miDelete: JMenuItem = JMenuItem("Remove image")

    init {
        miDelete.addActionListener { panel.removeImage() }
        add(miDelete)
    }
}