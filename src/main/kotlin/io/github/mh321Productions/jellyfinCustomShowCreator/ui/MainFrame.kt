package io.github.mh321Productions.jellyfinCustomShowCreator.ui

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.SeasonTab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.ShowTab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.Tab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu.MainMenuBar
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JTabbedPane
import kotlin.system.exitProcess

class MainFrame(rootDir: File) : JFrame() {

    companion object {
        const val TITLE_BASE = "Jellyfin Custom Show Creator"
    }

    var show = ShowInfo()
        private set

    var isDirty = false
        private set

    var rootDir = rootDir
        private set

    private val tabMain: JTabbedPane

    init {
        title = TITLE_BASE
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        size = Dimension(1280, 720)
        isResizable = true
        setLocationRelativeTo(null)

        jMenuBar = MainMenuBar(this, ::onOpen, ::onSave)
        layout = MigLayout("", "[grow]", "[grow]")

        tabMain = JTabbedPane()
        tabMain.addTab(ShowTab(this))
        tabMain.addTab(SeasonTab(this))
        add(tabMain, "cell 0 0, grow")

        addWindowClosingListener(::closeRequested)
    }

    fun markDirty() {
        isDirty = true
        title = createTitle()
    }

    private fun onOpen() {
        //TODO: Open and read project
    }

    private fun onSave() {
        if (!isDirty) return

        //TODO: Implement save

        isDirty = false
        title = createTitle()
    }

    private fun closeRequested() {
        if (isDirty) {
            when (JOptionPane.showConfirmDialog(this, "Do You want to save before closing?", "Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION)) {
                JOptionPane.YES_OPTION -> onSave()
                JOptionPane.CANCEL_OPTION -> return
            }
        }
        dispose()
        exitProcess(0)
    }

    private fun createTitle(): String {
        val unsavedChanges = if (isDirty) " *" else ""
        val show = if (show.name.isEmpty()) "" else " - ${show.name}"

        return "$TITLE_BASE$show$unsavedChanges"
    }

    private fun JTabbedPane.addTab(tab: Tab) = addTab(tab.title, tab.icon, tab)
    private fun addWindowClosingListener(listener: () -> Unit) = addWindowListener(object: WindowAdapter() {
        override fun windowClosing(e: WindowEvent) = listener()
    })
}