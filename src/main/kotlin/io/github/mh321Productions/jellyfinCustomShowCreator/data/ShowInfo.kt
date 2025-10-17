package io.github.mh321Productions.jellyfinCustomShowCreator.data

import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiEntry
import io.github.mh321Productions.jellyfinCustomShowCreator.data.enums.ShowStatus
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.PropertyType
import kotlinx.serialization.Serializable

@Serializable
data class ShowInfo(
    @UiEntry(0, PropertyType.TextField, "Show name")
    var name: String = "Test",

    @UiEntry(1, PropertyType.TextArea)
    var description: String = "Test\nTest",

    @UiEntry(2, PropertyType.Enum)
    var status: ShowStatus = ShowStatus.Continuing,

    val seasons: MutableList<SeasonInfo> = mutableListOf(),
)
