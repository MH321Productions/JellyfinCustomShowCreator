package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes.EpisodeListPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes.UnassignedEpisodePanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.MetadataPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors.ShowDataExtractor
import net.miginfocom.swing.MigLayout
import javax.swing.JScrollPane
import javax.swing.UIManager
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder

class ShowTab(frame: MainFrame) : Tab(frame, "Show info", null) {

    private val borderColor = UIManager.getColor("Component.borderColor")

    private val spMeta: JScrollPane
    private val spEpisodes: JScrollPane

    private val panelEpisodes: EpisodeListPanel<UnassignedEpisodePanel>
    private val panelMetadata: MetadataPanel<ShowInfo>

    init {
        layout = MigLayout("", "[grow 60][grow 20]", "[grow]")

        panelEpisodes = EpisodeListPanel<UnassignedEpisodePanel>(frame)
        spEpisodes = JScrollPane(panelEpisodes)
        spEpisodes.border = TitledBorder(LineBorder(borderColor), "Episodes", TitledBorder.LEADING, TitledBorder.TOP)
        add(spEpisodes, "cell 0 0, grow")

        panelMetadata = MetadataPanel(frame, frame.show, ShowDataExtractor())
        spMeta = JScrollPane(panelMetadata)
        spMeta.border = TitledBorder(LineBorder(borderColor), "Metadata", TitledBorder.LEADING, TitledBorder.TOP)
        add(spMeta, "cell 1 0, grow")
    }
}