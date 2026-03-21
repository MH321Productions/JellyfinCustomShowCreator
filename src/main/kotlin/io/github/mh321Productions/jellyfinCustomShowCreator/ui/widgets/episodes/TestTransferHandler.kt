package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeId
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import java.awt.datatransfer.Transferable
import javax.swing.DefaultListModel
import javax.swing.JComponent
import javax.swing.JList
import javax.swing.TransferHandler

class TestTransferHandler(private val frame: MainFrame) : TransferHandler() {

    private var index = -1
    private var beforeIndex = false

    override fun getSourceActions(c: JComponent) = MOVE or LINK

    @Suppress("UNCHECKED_CAST")
    override fun canImport(info: TransferSupport): Boolean {
        if (!info.isDrop || !info.isDataFlavorSupported(EpisodeId.dataFlavor)) return false

        val id = info.transferable.getTransferData(EpisodeId.dataFlavor) as EpisodeId
        if (id.isSeasonHeader) return false

        val loc = info.dropLocation as JList.DropLocation
        val insert = loc.isInsert

        val moveSupported = (MOVE and info.sourceDropActions) == MOVE
        if (moveSupported && insert) {
            info.dropAction = MOVE
            return true
        }

        val linkSupported = (LINK and info.sourceDropActions) == LINK
        if (linkSupported && !insert) {
            val list = info.component as JList<EpisodeId>
            val target = list.model.getElementAt(loc.index)

            if (target.isSeasonHeader) return false

            info.dropAction = LINK
            return true
        }

        return false
    }

    @Suppress("UNCHECKED_CAST")
    override fun importData(info: TransferSupport): Boolean {
        if (!info.isDrop) return false

        val list = info.component as JList<EpisodeId>
        val id = info.transferable.getTransferData(EpisodeId.dataFlavor) as EpisodeId
        val model = list.model as DefaultListModel<EpisodeId>

        //TODO: Actually move the episode
        if (list.dropLocation.isInsert) model.add(list.dropLocation.index, id)
        else model.set(list.dropLocation.index, id)
        beforeIndex = list.dropLocation.isInsert && list.dropLocation.index < index

        return true
    }

    @Suppress("UNCHECKED_CAST")
    override fun createTransferable(comp: JComponent): Transferable? {
        val list = comp as JList<EpisodeId>
        val id = list.selectedValue ?: return null
        index = list.selectedIndex

        println("Out: $id, $index")

        return EpisodeTransferable(id)
    }

    @Suppress("UNCHECKED_CAST")
    override fun exportDone(source: JComponent, data: Transferable?, action: Int) {
        if (action != MOVE || data !is EpisodeTransferable) return

        val list = source as JList<EpisodeId>
        val model = list.model as DefaultListModel<EpisodeId>

        println("Export done")

        if (beforeIndex) model.remove(index + 1)
        else model.remove(index)
    }
}