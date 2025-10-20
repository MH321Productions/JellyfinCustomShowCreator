package io.github.mh321Productions.jellyfinCustomShowCreator.data

import io.github.mh321Productions.jellyfinCustomShowCreator.data.enums.ShowStatus
import kotlinx.serialization.Serializable

@Serializable
data class ShowInfo(
    var name: String = "Test",
    var description: String = "Test\nTest",
    var status: ShowStatus = ShowStatus.Continuing,

    val seasons: MutableList<SeasonInfo> = mutableListOf(),
)
