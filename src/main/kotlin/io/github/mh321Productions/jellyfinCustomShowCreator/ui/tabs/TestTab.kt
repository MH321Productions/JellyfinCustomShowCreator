package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeId
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes.TestTransferHandler
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.list.CustomDefaultListCellRenderer
import net.miginfocom.swing.MigLayout
import javax.swing.DefaultListModel
import javax.swing.DropMode
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel

class TestTab(frame: MainFrame) : Tab(frame, "Test", null) {

    private val spEpisodes: JScrollPane?
    private val listEpisodes: JList<EpisodeId>
    private val modelEpisodes: DefaultListModel<EpisodeId> = DefaultListModel()

    init {
        layout = MigLayout("", "[grow]", "[grow]")

        modelEpisodes.addAll(buildList())

        listEpisodes = JList(modelEpisodes)
        listEpisodes.cellRenderer = CustomDefaultListCellRenderer {
            val name = it.getName(frame.show)

            if (it.isSeasonHeader) "------------ $name ----------"
            else name
        }
        listEpisodes.selectionMode = ListSelectionModel.SINGLE_SELECTION
        listEpisodes.dragEnabled = true
        listEpisodes.dropMode = DropMode.ON_OR_INSERT
        listEpisodes.transferHandler = TestTransferHandler(frame)
        listEpisodes.addListSelectionListener { println("Selected ${listEpisodes.selectedIndex} ${listEpisodes.selectedValue}") }

        spEpisodes = JScrollPane(listEpisodes)
        add(spEpisodes, "cell 0 0, grow")
    }

    override fun updateUI() {
        super.updateUI()

        spEpisodes?.border = ThemeUtils.createTitledBorder("Episodes")
    }

    override fun updateData() {
        modelEpisodes.clear()
        modelEpisodes.addAll(buildList())
    }

    private fun buildList(): List<EpisodeId> {
        val list = mutableListOf<EpisodeId>()
        list.addAll(
            frame.show.seasons.flatMap {
                listOf(EpisodeId(it.number, -1)) +
                it.episodes.map {
                        episode -> EpisodeId.fromEpisode(frame.show, episode)
                }
            }
        )

        list.add(EpisodeId(-1, -1))
        list.addAll(frame.show.unassignedEpisodes.map { EpisodeId.fromEpisode(frame.show, it) })

        return list
    }
}