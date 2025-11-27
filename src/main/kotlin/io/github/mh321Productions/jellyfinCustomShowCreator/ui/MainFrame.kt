package io.github.mh321Productions.jellyfinCustomShowCreator.ui

import io.github.mh321Productions.jellyfinCustomShowCreator.io.parser.ProjectParser
import io.github.mh321Productions.jellyfinCustomShowCreator.io.worker.FileScannerWorker
import io.github.mh321Productions.jellyfinCustomShowCreator.io.worker.Worker
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.EpisodeTab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.SeasonTab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.ShowTab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs.Tab
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.menu.MainMenuBar
import kotlinx.coroutines.*
import kotlinx.coroutines.swing.Swing
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import javax.swing.*
import kotlin.system.exitProcess

class MainFrame(rootDir: File) : JFrame() {

    companion object {
        const val TITLE_BASE = "Jellyfin Custom Show Creator"
    }

    var show = ProjectParser.parseProject(rootDir)
        private set

    var isDirty = false
        private set

    var rootDir = rootDir
        private set

    private val tabMain: JTabbedPane
    private val tabShow: ShowTab
    private val tabSeason: SeasonTab
    private val tabEpisodes: EpisodeTab

    init {
        title = createTitle()
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        size = Dimension(1280, 720)
        isResizable = true
        setLocationRelativeTo(null)

        jMenuBar = MainMenuBar(this, ::onOpen, ::onSave)
        layout = MigLayout("", "[grow]", "[grow][]")

        tabMain = JTabbedPane()
        tabShow = ShowTab(this)
        tabSeason = SeasonTab(this)
        tabEpisodes = EpisodeTab(this)

        tabMain.addTab(tabShow)
        tabMain.addTab(tabSeason)
        tabMain.addTab(tabEpisodes)
        add(tabMain, "cell 0 0, grow, wrap")

        addWindowClosingListener(::closeRequested)

        startWorker(FileScannerWorker())
    }

    fun markDirty() {
        isDirty = true
        title = createTitle()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun startWorker(worker: Worker) = GlobalScope.launch(Dispatchers.Swing) {
        val progressBar = JProgressBar()
        add(progressBar, "grow, wrap")

        worker.doWork(this@MainFrame, progressBar)

        remove(progressBar)
        revalidate()
        repaint()
    }

    fun updateData() {
        tabShow.updateData()
        tabSeason.updateData()
        tabEpisodes.updateData()
    }

    private fun onOpen() {
        if (isDirty) {
            when (JOptionPane.showConfirmDialog(this, "Do You want to save before opening a new folder?", "Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION)) {
                JOptionPane.YES_OPTION -> if (!onSave()) return
                JOptionPane.CANCEL_OPTION -> return
            }
        }

        val fc = JFileChooser(rootDir)
        fc.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        fc.dialogTitle = "Select a project directory"
        fc.isMultiSelectionEnabled = false

        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return

        rootDir = fc.selectedFile
        show = ProjectParser.parseProject(rootDir)
        isDirty = false
        title = createTitle()

        updateData()
        startWorker(FileScannerWorker())
    }

    private fun onSave(): Boolean {
        if (!isDirty) return true

        if (ProjectParser.saveProject(rootDir, show)) {
            isDirty = false
            title = createTitle()
            return true
        }

        return false
    }

    private fun closeRequested() {
        if (isDirty) {
            when (JOptionPane.showConfirmDialog(this, "Do You want to save before closing?", "Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION)) {
                JOptionPane.YES_OPTION -> if (!onSave()) return
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