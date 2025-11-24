package io.github.mh321Productions.jellyfinCustomShowCreator.data

import io.github.mh321Productions.jellyfinCustomShowCreator.data.enums.ShowStatus
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ShowInfo(
    var name: String = "Custom Show",
    var description: String = "This is a custom show\nmade with the Custom Show Creator",
    var status: ShowStatus = ShowStatus.Ended,
    var posterFile: String = "",
    var year: Int = LocalDateTime.now().year,

    val seasons: MutableList<SeasonInfo> = mutableListOf(SeasonInfo()),
    val unassignedEpisodes: MutableList<EpisodeInfo> = mutableListOf(),
)
