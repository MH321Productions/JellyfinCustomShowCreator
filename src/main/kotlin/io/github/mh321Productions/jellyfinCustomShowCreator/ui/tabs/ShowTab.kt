package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images.ImagePanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.MetadataPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors.ShowDataExtractor
import net.miginfocom.swing.MigLayout
import javax.swing.JScrollPane

class ShowTab(frame: MainFrame) : Tab(frame, "Show info", null) {

    //Note: The scroll panes are never really null, but can be when not initialized during a call of updateUI
    private val spMeta: JScrollPane?
    private val spPoster: JScrollPane?

    private val panelPoster: ImagePanel
    private val panelMetadata: MetadataPanel<ShowInfo>

    init {
        layout = MigLayout("", "[grow 40][grow 60]", "[grow]")

        panelPoster = ImagePanel(frame, frame.show.posterFile) { frame.show.posterFile = it }
        spPoster = JScrollPane(panelPoster)
        spPoster.border = ThemeUtils.createTitledBorder("Poster")
        add(spPoster, "cell 0 0, grow")

        panelMetadata = MetadataPanel(frame, frame.show, ShowDataExtractor(frame))
        spMeta = JScrollPane(panelMetadata)
        spMeta.border = ThemeUtils.createTitledBorder("Metadata")
        add(spMeta, "cell 1 0, grow")
    }

    override fun updateUI() {
        super.updateUI()

        spPoster?.border = ThemeUtils.createTitledBorder("Poster")
        spMeta?.border = ThemeUtils.createTitledBorder("Metadata")
    }

    override fun updateData() {
        panelMetadata.data = frame.show
        panelPoster.setImage(frame.show.posterFile)
    }
}