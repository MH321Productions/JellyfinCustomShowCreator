package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes.EpisodeListPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes.UnassignedEpisodePanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.MetadataPanel
import net.miginfocom.swing.MigLayout
import javax.swing.JScrollPane

class ShowTab(frame: MainFrame) : Tab(frame, "Show info", null) {

    private val spMeta: JScrollPane
    private val spEpisodes: JScrollPane

    private val panelEpisodes: EpisodeListPanel<UnassignedEpisodePanel>
    private val panelMetadata: MetadataPanel<ShowInfo>

    init {
        layout = MigLayout("", "[grow 60][grow 40]", "[grow]")

        panelEpisodes = EpisodeListPanel<UnassignedEpisodePanel>(frame)
        spEpisodes = JScrollPane(panelEpisodes)
        add(spEpisodes, "cell 0 0, grow")

        panelMetadata = MetadataPanel(frame, frame.show)
        spMeta = JScrollPane(panelMetadata)
        add(spMeta, "cell 1 0, grow")
    }
}