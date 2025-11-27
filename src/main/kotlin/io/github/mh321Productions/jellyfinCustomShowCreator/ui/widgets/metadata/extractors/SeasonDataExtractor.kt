package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors

import io.github.mh321Productions.jellyfinCustomShowCreator.data.SeasonInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame

class SeasonDataExtractor(frame: MainFrame) : DataExtractor<SeasonInfo>(frame) {
    override fun getWidgets(data: SeasonInfo) = listOf(
        label("Number: ", data.number.toString())
    )
}