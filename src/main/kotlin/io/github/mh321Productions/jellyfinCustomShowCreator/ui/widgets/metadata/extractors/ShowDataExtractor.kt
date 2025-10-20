package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo

class ShowDataExtractor : DataExtractor<ShowInfo>() {
    override fun getWidgets(data: ShowInfo) = listOf(
        textField("Show Name: ", data.name) { data.name = it },
        textArea("Show Description: ", data.description) { data.description = it },
        comboBox("Show Status: ", data.status) { data.status = it },
    )
}