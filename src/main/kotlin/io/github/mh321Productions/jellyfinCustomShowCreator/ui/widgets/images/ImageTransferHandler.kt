package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import java.awt.datatransfer.DataFlavor
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.JOptionPane
import javax.swing.TransferHandler

class ImageTransferHandler(private val frame: MainFrame) : TransferHandler() {

    override fun canImport(info: TransferSupport): Boolean {
        if (!info.isDrop || !info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) return false

        val copySupported = (COPY and info.sourceDropActions) == COPY
        if (copySupported) {
            info.dropAction = COPY
            return true
        }

        return false
    }

    override fun getSourceActions(c: JComponent) = COPY

    @Suppress("UNCHECKED_CAST")
    override fun importData(info: TransferSupport): Boolean {
        if (!info.isDrop) return false

        val panel = info.component as ImagePanel
        val files = mutableListOf<File>()
        try {
            files.addAll(info.transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<File>)
        } catch (_: Exception) {
            return false
        }

        if (files.isEmpty()) return false
        else if (files.size > 1) {
            JOptionPane.showMessageDialog(frame, "Only one image is allowed", "Import failed", JOptionPane.ERROR_MESSAGE)
            return false
        }

        return panel.setImage(files[0])
    }
}