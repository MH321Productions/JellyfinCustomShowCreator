package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images.ImagePanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.MetadataPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors.EpisodeDataExtractor
import net.miginfocom.swing.MigLayout
import javax.swing.JPanel
import javax.swing.JScrollPane

class EpisodeTab(frame: MainFrame) : Tab(frame, "Episodes", null) {

    private val spEpisodes: JScrollPane?
    private val spMeta: JScrollPane?
    private val panelThumbnail: ImagePanel?

    private val panelMeta: MetadataPanel<EpisodeInfo>

    init {
        layout = MigLayout("", "[grow 10][grow 90]", "[grow][grow]")

        spEpisodes = JScrollPane(JPanel()) //TODO: Use Tree
        spEpisodes.border = ThemeUtils.createTitledBorder("Episodes")
        add(spEpisodes, "cell 0 0 1 2, grow")

        panelThumbnail = ImagePanel(frame, "") {  }
        panelThumbnail.border = ThemeUtils.createTitledBorder("Thumbnail")
        add(panelThumbnail, "cell 1 0, grow")

        panelMeta = MetadataPanel(frame, EpisodeInfo(), EpisodeDataExtractor(frame)) //TODO: Use current episode
        spMeta = JScrollPane(panelMeta)
        spMeta.border = ThemeUtils.createTitledBorder("Metadata")
        add(spMeta, "cell 1 1, grow")
    }

    override fun updateData() {

    }

    override fun updateUI() {
        super.updateUI()

        spEpisodes?.border = ThemeUtils.createTitledBorder("Episodes")
        spMeta?.border = ThemeUtils.createTitledBorder("Metadata")
        panelThumbnail?.border = ThemeUtils.createTitledBorder("Thumbnail")
    }
}