package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.util.getEpisode
import io.github.mh321Productions.jellyfinCustomShowCreator.util.getSeason
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.ThemeUtils
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.images.ImagePanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.MetadataPanel
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors.EpisodeDataExtractor
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.tree.CustomDefaultTreeCellRenderer
import io.github.mh321Productions.jellyfinCustomShowCreator.util.getEpisodeOrNull
import net.miginfocom.swing.MigLayout
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class EpisodeTab(frame: MainFrame) : Tab(frame, "Episodes", null) {

    companion object {
        private const val MISSING_EPISODE = "<Missing Episode>"
    }

    private val spEpisodes: JScrollPane?
    private val spMeta: JScrollPane?
    private val panelThumbnail: ImagePanel?

    private val panelMeta: MetadataPanel<EpisodeInfo>
    private val treeEpisodes: JTree

    private var selectedIndex = Pair(-1, -1)

    private val currentEpisode: EpisodeInfo
        get() =
            if (selectedIndex.first == -1)
                frame.show.unassignedEpisodes[selectedIndex.second]
            else
                frame.show.seasons.getSeason(selectedIndex.first).episodes.getEpisode(selectedIndex.second)

    init {
        layout = MigLayout("", "[grow 10][grow 90]", "[grow][grow]")

        treeEpisodes = JTree(buildTree())
        treeEpisodes.cellRenderer = CustomDefaultTreeCellRenderer(::getNodeName)
        treeEpisodes.showsRootHandles = true
        treeEpisodes.isRootVisible = false
        treeEpisodes.addTreeSelectionListener { onSelectEpisode() }
        spEpisodes = JScrollPane(treeEpisodes)
        spEpisodes.border = ThemeUtils.createTitledBorder("Episodes")
        add(spEpisodes, "cell 0 0 1 2, grow")

        panelThumbnail = ImagePanel(frame, "") {  }
        panelThumbnail.border = ThemeUtils.createTitledBorder("Thumbnail")
        add(panelThumbnail, "cell 1 0, grow")

        panelMeta = MetadataPanel(frame, EpisodeInfo(), EpisodeDataExtractor(frame))
        spMeta = JScrollPane(panelMeta)
        spMeta.border = ThemeUtils.createTitledBorder("Metadata")
        add(spMeta, "cell 1 1, grow")
    }

    override fun updateData() {
        treeEpisodes.model = DefaultTreeModel(buildTree())
        revalidate()
    }

    override fun updateUI() {
        super.updateUI()

        spEpisodes?.border = ThemeUtils.createTitledBorder("Episodes")
        spMeta?.border = ThemeUtils.createTitledBorder("Metadata")
        panelThumbnail?.border = ThemeUtils.createTitledBorder("Thumbnail")
    }

    private fun buildTree(): DefaultMutableTreeNode {
        val root = DefaultMutableTreeNode(Pair(-2, -1))

        frame.show.seasons.forEach {
            val season = DefaultMutableTreeNode(Pair(it.number, -1))
            it.episodes.forEach { ep -> season.add(DefaultMutableTreeNode(Pair(it.number, ep.number))) }
            root.add(season)
        }

        val unassigned = DefaultMutableTreeNode(Pair(-1, -1))
        frame.show.unassignedEpisodes.forEachIndexed { index, _ -> unassigned.add(DefaultMutableTreeNode(Pair(-1, index))) }
        root.add(unassigned)

        return root
    }

    private fun getNodeName(index: Pair<Int, Int>): String {
        val (season, episode) = index
        return when (episode) {
            -1 -> when (season) {
                -2 -> "TV Show"
                -1 -> "Unassigned Episodes"
                0 -> "Specials"
                else -> "Season ${index.first}"
            }
            else -> when (season) {
                -1 -> frame.show.unassignedEpisodes.getOrNull(episode)?.title ?: MISSING_EPISODE
                else -> frame.show.seasons.getSeason(season).episodes.getEpisodeOrNull(episode)?.title ?: MISSING_EPISODE
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun onSelectEpisode() {
        val node = treeEpisodes.lastSelectedPathComponent as? DefaultMutableTreeNode ?: return
        selectedIndex = node.userObject as Pair<Int, Int>

        if (selectedIndex.second == -1) {
            panelThumbnail?.removeImage()
            panelThumbnail?.isEnabled = false

            panelMeta.isEnabled = false
            panelMeta.data = EpisodeInfo()
        } else {
            panelThumbnail?.isEnabled = true
            if (currentEpisode.thumbnail == EpisodeInfo.ATTACHMENT_THUMBNAIL) panelThumbnail?.setImage(currentEpisode.decodedThumbnail)
            else panelThumbnail?.setImage(currentEpisode.thumbnail)

            panelMeta.isEnabled = true
            panelMeta.data = currentEpisode
        }
    }
}