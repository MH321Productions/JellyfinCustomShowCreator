package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors

import io.github.mh321Productions.jellyfinCustomShowCreator.data.SeasonInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame

class SeasonDataExtractor(frame: MainFrame) : DataExtractor<SeasonInfo>(frame) {
    override fun getWidgets(data: SeasonInfo) = listOf(
        numberSpinner("Number", data.number, 0, Int.MAX_VALUE, 1, "#") { data.number = it.toInt() },
    )
}