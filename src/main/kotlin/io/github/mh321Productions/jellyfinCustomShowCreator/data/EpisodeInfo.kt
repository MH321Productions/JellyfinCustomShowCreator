package io.github.mh321Productions.jellyfinCustomShowCreator.data

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeInfo(
    var name: String = "",
    var number: Int = -1,
    var season: Int = -1,
    var originalFilename: String = "",
    var thumbnail: String = "",
)