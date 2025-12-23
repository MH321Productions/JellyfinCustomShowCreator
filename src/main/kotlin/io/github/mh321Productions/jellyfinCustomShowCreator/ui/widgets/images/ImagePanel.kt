package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu.ImagePopupMenu
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu.PopupMenuMouseListener
import io.github.mh321Productions.jellyfinCustomShowCreator.util.*
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JOptionPane
import javax.swing.JPanel
import kotlin.math.min

class ImagePanel(private val frame: MainFrame, imagePath: String, private val updateListener: (String) -> Unit) : JPanel() {

    private var image: BufferedImage? = loadImage(imagePath.relativeFile(frame.rootDir), true)

    init {
        transferHandler = ImageTransferHandler(frame)

        addMouseListener(PopupMenuMouseListener(ImagePopupMenu(this)))
    }

    override fun paint(g: Graphics) {
        super.paint(g)

        if (image == null) return

        val g2d = g as Graphics2D
        val (x, y, w, h) = getScaledRect()
        g2d.drawImage(image, x, y, w, h, this)
    }

    fun setImage(relativePath: String) {
        if (relativePath.isBlank()) removeImage()
        else setImage(relativePath.relativeFile(frame.rootDir))
    }

    fun setImage(path: File): Boolean {
        if (!path.isRelativeTo(frame.rootDir)) {
            JOptionPane.showMessageDialog(frame, "The image must be in the same or a subdirectory of the project file", "Import failed", JOptionPane.ERROR_MESSAGE)
            return false
        }

        val loadedImage = loadImage(path)
        if (loadedImage != null) {
            image = loadedImage
            updateListener(path.toRelativeString(frame.rootDir))
            frame.markDirty()
            repaint()
            return true
        }

        return false
    }

    fun setImage(image: BufferedImage?) {
        this.image = image
        repaint()
    }

    fun removeImage() {
        if (image == null) return

        image = null
        updateListener("")
        frame.markDirty()
        repaint()
    }

    private fun loadImage(file: File, silent: Boolean = false): BufferedImage? {
        try {
            return ImageIO.read(file)
        } catch (ex: IOException) {
            if (!silent) JOptionPane.showMessageDialog(frame, "The file is not an image or could not be read:\n${ex.message}", "Import failed", JOptionPane.ERROR_MESSAGE)
            return null
        }
    }

    private fun getScaleFactor(): Double {
        if (image == null) return 1.0

        val iw = image!!.width
        val ih = image!!.height

        val scaleX = width / iw.toDouble()
        val scaleY = height / ih.toDouble()

        return when {
            scaleX >= 1 && scaleY < 1 -> scaleY
            scaleX < 1 && scaleY >= 1 -> scaleX
            else -> min(scaleX, scaleY)
        }
    }

    private fun getScaledRect(): Rectangle {
        if (image == null) return Rectangle(0, 0, 0, 0)

        val scaleFactor = getScaleFactor()
        val newWidth = (image!!.width * scaleFactor).toInt()
        val newHeight = (image!!.height * scaleFactor).toInt()
        val newX = (width - newWidth) / 2
        val newY = (height - newHeight) / 2

        return Rectangle(newX, newY, newWidth, newHeight)
    }
}