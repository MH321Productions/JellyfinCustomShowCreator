package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.dialogs.AboutDialog
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.dialogs.HelpDialog
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.WindowEvent
import java.io.File
import javax.swing.*

class MainMenuBar(private val frame: MainFrame, private val onOpen: () -> Unit, private val onSave: () -> Unit) : JMenuBar() {

    private val menuFile = JMenu("File")
    private val menuHelp = JMenu("Help")

    private val miOpen = JMenuItem("Open project folder")
    private val miSave = JMenuItem("Save project")
    private val miQuit = JMenuItem("Quit")

    private val miHelp = JMenuItem("Help")
    private val miAbout = JMenuItem("About")

    init {
        miOpen.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)
        miOpen.addActionListener { onOpen() }
        menuFile.add(miOpen)

        miSave.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)
        miSave.addActionListener { onSave() }
        menuFile.add(miSave)

        menuFile.addSeparator()

        miQuit.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK)
        miQuit.addActionListener { frame.dispatchEvent(WindowEvent(frame, WindowEvent.WINDOW_CLOSING)) }
        menuFile.add(miQuit)
        add(menuFile)

        miHelp.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)
        miHelp.addActionListener { HelpDialog(frame) }
        menuHelp.add(miHelp)

        menuHelp.addSeparator()

        miAbout.addActionListener { AboutDialog(frame) }
        menuHelp.add(miAbout)
        add(menuHelp)
    }
}