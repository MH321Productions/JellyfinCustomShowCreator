package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes

import java.awt.datatransfer.Transferable
import javax.swing.JComponent
import javax.swing.TransferHandler

class EpisodeTransferHandler : TransferHandler() {

    override fun canImport(info: TransferSupport): Boolean {
        return false
    }

    override fun getSourceActions(c: JComponent) = MOVE

    override fun importData(info: TransferSupport): Boolean {
        return false
    }

    override fun createTransferable(c: JComponent): Transferable? {
        return super.createTransferable(c)
    }
}