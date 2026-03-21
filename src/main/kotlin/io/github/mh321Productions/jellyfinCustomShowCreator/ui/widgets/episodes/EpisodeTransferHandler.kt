package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeId
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import javax.swing.JComponent
import javax.swing.JTree
import javax.swing.TransferHandler
import javax.swing.tree.DefaultMutableTreeNode

class EpisodeTransferHandler : TransferHandler() {

    override fun canImport(info: TransferSupport): Boolean {
        if (!info.isDrop && info.isDataFlavorSupported(DataFlavor.stringFlavor)) return false

        val moveSupported = (MOVE and info.sourceDropActions) == MOVE
        if (moveSupported) {
            info.dropAction = MOVE
            return true
        }

        return false
    }

    override fun getSourceActions(c: JComponent) = MOVE

    override fun importData(info: TransferSupport): Boolean {
        if (!info.isDrop) return false

        val tree = info.component as JTree
        val root = tree.model.root as DefaultMutableTreeNode
        val loc = tree.dropLocation// ?: return false
        val path = loc.path// ?: return false
        val index = loc.childIndex
        val id = info.transferable.getTransferData(EpisodeId.dataFlavor) as EpisodeId

        println("In: $path -> $index")

        if (path.pathCount < 2) {
            if (index == 0) return false

            val seasonNode = root.getChildAt(index - 1) as DefaultMutableTreeNode
            seasonNode.add(DefaultMutableTreeNode(Pair(id.season, id.episode)))
            return true
        }


        return true
    }

    override fun createTransferable(c: JComponent): Transferable {
        val tree = c as JTree
        val selection = tree.selectionPath!!
        val node = selection.lastPathComponent as DefaultMutableTreeNode
        val entry = node.userObject as Pair<Int, Int>
        println("Out: ${entry.first}, ${entry.second}")

        return EpisodeTransferable(entry)
    }
}