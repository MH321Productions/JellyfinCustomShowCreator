package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame

class EpisodeDataExtractor(frame: MainFrame) : DataExtractor<EpisodeInfo>(frame) {
    override fun getWidgets(data: EpisodeInfo) = listOf(
        textField("Title: ", data.title) { data.title = it },
    )
}