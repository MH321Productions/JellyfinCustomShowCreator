package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.SeasonInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.dialogs.SeasonNumberDialog
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images.ImagePanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.list.CustomDefaultListCellRenderer
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.MetadataPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors.SeasonDataExtractor
import net.miginfocom.swing.MigLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import kotlin.math.min

class SeasonTab(frame: MainFrame) : Tab(frame, "Seasons", null) {

    //Note: The components are never really null, but can be when not initialized during a call of updateUI
    private val panelList: JPanel?
    private val spMeta: JScrollPane?
    private val panelPoster: ImagePanel?

    private val panelMetadata: MetadataPanel<SeasonInfo>
    private val listSeasons: JList<Int>
    private val btnAddSeason: JButton
    private val btnRemoveSeason: JButton
    private val chkbHasSpecials: JCheckBox

    private val seasonListModel: DefaultListModel<Int>

    private var selectedSeasonIndex: Int
        get() = if (listSeasons.selectedIndex == -1) 0 else listSeasons.selectedIndex
        set(value) { listSeasons.selectedIndex = value }

    private val seasons: MutableList<SeasonInfo>
        get() = frame.show.seasons

    private val currentSeason: SeasonInfo
        get() = seasons[selectedSeasonIndex]


    init {
        layout = MigLayout("", "[grow 5][grow 40][grow 55]", "[grow]")

        panelList = JPanel()
        panelList.border = ThemeUtils.createTitledBorder("Seasons")
        panelList.layout = MigLayout("", "[grow][grow]", "[][grow][]")

        chkbHasSpecials = JCheckBox("Add Specials", seasons.first().number == 0)
        chkbHasSpecials.addActionListener { onCheckHasSpecials() }
        panelList.add(chkbHasSpecials, "cell 0 0 2 1, grow")

        seasonListModel = DefaultListModel()
        seasonListModel.addAll(frame.show.seasons.map { it.number })
        listSeasons = JList(seasonListModel)
        listSeasons.selectionMode = ListSelectionModel.SINGLE_SELECTION
        listSeasons.selectedIndex = 0
        listSeasons.cellRenderer = CustomDefaultListCellRenderer { if (it == 0) "Specials" else "Season $it" }
        listSeasons.addListSelectionListener { if (!it.valueIsAdjusting) onSelectSeason() }
        listSeasons.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) onEditSeasonNumber(listSeasons.locationToIndex(e.point))
            }
        })
        panelList.add(JScrollPane(listSeasons), "cell 0 1 2 1, grow")

        btnAddSeason = JButton("Add season")
        btnAddSeason.addActionListener { onAddSeason() }
        panelList.add(btnAddSeason, "cell 0 2, grow")

        btnRemoveSeason = JButton("Remove season")
        btnRemoveSeason.addActionListener { onRemoveSeason() }
        panelList.add(btnRemoveSeason, "cell 1 2, grow")

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

    override fun updateData() {
        chkbHasSpecials.isEnabled = seasons.first().number == 0
        seasonListModel.clear()
        seasonListModel.addAll(seasons.map { it.number })
    }

    private fun onSelectSeason() {
        btnRemoveSeason.isEnabled = listSeasons.selectedValue != 0
        panelMetadata.data = currentSeason
        panelPoster?.setImage(currentSeason.posterFile)
    }

    private fun onAddSeason() {
        val newNumber = seasonListModel.lastElement() + 1
        seasonListModel.addElement(newNumber)
        seasons.add(SeasonInfo(newNumber))
        selectedSeasonIndex = seasonListModel.size() - 1
        frame.markDirty()
    }

    private fun onRemoveSeason() {
        val minSize = if (chkbHasSpecials.isSelected) 2 else 1
        if (seasonListModel.size > minSize) {
            val index = selectedSeasonIndex
            seasonListModel.remove(selectedSeasonIndex)
            selectedSeasonIndex = min(index, seasonListModel.size() - 1)

            val season = seasons.removeAt(index)
            frame.show.unassignedEpisodes.addAll(season.episodes)

            frame.markDirty()
        }
    }

    private fun onCheckHasSpecials() {
        if (chkbHasSpecials.isSelected) {
            if (frame.show.seasons.first().number != 0) {
                frame.show.seasons.addFirst(SeasonInfo(0))
                seasonListModel.add(0, 0)
                frame.markDirty()
            }
        } else {
            if (frame.show.seasons.first().number == 0) {
                frame.show.seasons.removeFirst()
                seasonListModel.remove(0)
                frame.markDirty()
            }
        }
    }

    private fun onEditSeasonNumber(index: Int) {
        if (index == -1 || seasons[index].number == 0) return

        val oldNumber = seasons[index].number
        val newNumber = SeasonNumberDialog(frame, oldNumber).newSeasonNumber

        if (newNumber == -1 || oldNumber == newNumber) return

        val s = seasons.removeAt(index)
        s.number = newNumber

        val numbers = seasons.map { it.number }
        var newIndex: Int

        if (numbers.contains(newNumber)) {
            newIndex = numbers.indexOf(newNumber)

            var i = newIndex
            var n = newNumber

            while (i < numbers.size && numbers[i] == n) {
                seasons[i].number++
                i++
                n++
            }
        } else {
            newIndex = numbers.indexOfFirst { it > newNumber }
            if (newIndex == -1) newIndex = numbers.size
        }

        seasons.add(newIndex, s)
        seasonListModel.clear()
        seasonListModel.addAll(seasons.map { it.number })
        selectedSeasonIndex = newIndex
        frame.markDirty()
    }
}