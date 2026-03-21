package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.episodes

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeId
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable

data class EpisodeTransferable(val id: EpisodeId) : Transferable {
    companion object {
        private val supportedFlavors = arrayOf(EpisodeId.dataFlavor)
    }

    constructor(entry: Pair<Int, Int>) : this(EpisodeId(entry.first, entry.second))

    override fun getTransferDataFlavors() = supportedFlavors
    override fun isDataFlavorSupported(dataFlavor: DataFlavor?) = supportedFlavors.contains(dataFlavor)

    override fun getTransferData(dataFlavor: DataFlavor?): EpisodeId {
        if (!isDataFlavorSupported(dataFlavor)) throw IllegalArgumentException("$dataFlavor is not supported")

        return id
    }

}
