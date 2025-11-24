package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.SeasonInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images.ImagePanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.MetadataPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors.SeasonDataExtractor
import net.miginfocom.swing.MigLayout
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel

class SeasonTab(frame: MainFrame) : Tab(frame, "Season infos", null) {

    //Note: The components are never really null, but can be when not initialized during a call of updateUI
    private val panelList: JPanel?
    private val spMeta: JScrollPane?
    private val panelPoster: ImagePanel?

    private val panelMetadata: MetadataPanel<SeasonInfo>
    private val listSeasons: JList<Int>
    private val btnAddSeason: JButton
    private val btnRemoveSeason: JButton

    private val seasonListModel: DefaultListModel<Int>

    private val selectedSeasonIndex: Int
        get() = if (listSeasons.selectedIndex == -1) 0 else listSeasons.selectedIndex

    private val currentSeason: SeasonInfo
        get() = frame.show.seasons[selectedSeasonIndex]


    init {
        layout = MigLayout("", "[grow 10][grow 40][grow 30]", "[grow]")

        panelList = JPanel()
        panelList.border = ThemeUtils.createTitledBorder("Seasons")
        panelList.layout = MigLayout("", "[grow][grow]", "[grow][]")

        seasonListModel = DefaultListModel()
        seasonListModel.addAll(frame.show.seasons.map { it.number })
        listSeasons = JList(seasonListModel)
        listSeasons.selectionMode = ListSelectionModel.SINGLE_SELECTION
        listSeasons.selectedIndex = 0
        listSeasons.addListSelectionListener { onSelectSeason() }
        panelList.add(JScrollPane(listSeasons), "cell 0 0 2 1, grow")

        btnAddSeason = JButton("Add season")
        btnAddSeason.addActionListener { onAddSeason() }
        panelList.add(btnAddSeason, "cell 0 1, grow")

        btnRemoveSeason = JButton("Remove season")
        btnRemoveSeason.addActionListener { onRemoveSeason() }
        panelList.add(btnRemoveSeason, "cell 1 1, grow")

        add(panelList, "cell 0 0, grow")

        panelPoster = ImagePanel(frame, currentSeason.posterFile) { currentSeason.posterFile = it }
        panelPoster.border = ThemeUtils.createTitledBorder("Poster")
        add(panelPoster, "cell 1 0, grow")

        panelMetadata = MetadataPanel(frame, currentSeason, SeasonDataExtractor(frame))
        spMeta = JScrollPane(panelMetadata)
        spMeta.border = ThemeUtils.createTitledBorder("Metadata")
        add(spMeta, "cell 2 0, grow")
    }

    override fun updateUI() {
        super.updateUI()

        panelPoster?.border = ThemeUtils.createTitledBorder("Poster")
        spMeta?.border = ThemeUtils.createTitledBorder("Metadata")
        panelList?.border = ThemeUtils.createTitledBorder("Seasons")
    }

    private fun onSelectSeason() {

    }

    private fun onAddSeason() {
        seasonListModel.addElement(seasonListModel.lastElement() + 1)
    }

    private fun onRemoveSeason() {
        if (seasonListModel.size > 1)
            seasonListModel.remove(selectedSeasonIndex)
    }
}