package io.github.mh321Productions.jellyfinCustomShowCreator.ui

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.SeasonTab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.ShowTab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.Tab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu.MainMenuBar
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JTabbedPane

class MainFrame : JFrame() {

    var useAutoTheme = true
        private set

    var show = ShowInfo()
        private set

    var isDirty = false
        private set

    private val tabMain: JTabbedPane

    init {
        title = "Jellyfin Custom Show Creator"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1280, 720)
        isResizable = true
        setLocationRelativeTo(null)

        jMenuBar = MainMenuBar(this)
        layout = MigLayout("", "[grow]", "[grow]")

        tabMain = JTabbedPane()
        tabMain.addTab(ShowTab(this))
        tabMain.addTab(SeasonTab(this))
        add(tabMain, "cell 0 0, grow")
    }

    fun markDirty() {
        isDirty = true
        //TODO: Handle title and quit/save logic
    }

    private fun JTabbedPane.addTab(tab: Tab) = addTab(tab.title, tab.icon, tab)
}