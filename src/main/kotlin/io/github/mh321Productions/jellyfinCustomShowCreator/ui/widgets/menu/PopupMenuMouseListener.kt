package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPopupMenu

class PopupMenuMouseListener(private val popup: JPopupMenu) : MouseAdapter() {
    override fun mousePressed(e: MouseEvent) = maybeShowPopup(e)
    override fun mouseReleased(e: MouseEvent) = maybeShowPopup(e)

    private fun maybeShowPopup(e: MouseEvent) {
        if (e.isPopupTrigger) {
            popup.show(e.component, e.x, e.y)
        }
    }
}