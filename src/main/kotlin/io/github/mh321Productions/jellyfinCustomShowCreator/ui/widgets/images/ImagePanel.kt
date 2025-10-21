package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel

class ImagePanel(private val frame: MainFrame, image: BufferedImage?, private val updateListener: (BufferedImage?) -> Unit) : JPanel() {

    var image: BufferedImage? = image
        set(value) {
            field = value
            updateListener(value)
            frame.markDirty()
            repaint()
        }

    init {
        transferHandler = ImageTransferHandler(frame)
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        if (image == null) return

        val g2d = g as Graphics2D
        g2d.drawImage(image, null, 0, 0)
    }
}