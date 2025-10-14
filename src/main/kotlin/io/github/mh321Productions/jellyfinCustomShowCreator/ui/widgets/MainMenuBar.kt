package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.dialogs.AboutDialog
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.dialogs.HelpDialog
import java.awt.event.KeyEvent
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.KeyStroke

class MainMenuBar(private val frame: MainFrame) : JMenuBar() {

    private val menuFile = JMenu("File")
    private val menuHelp = JMenu("Help")

    private val miOpen = JMenuItem("Open folder")


    private val miHelp = JMenuItem("Help")
    private val miAbout = JMenuItem("About")

    init {
        miOpen.addActionListener {
            JOptionPane.showMessageDialog(frame, "File opened", "Test", JOptionPane.INFORMATION_MESSAGE)
        }
        menuFile.add(miOpen)
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