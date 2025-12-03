package io.github.mh321Productions.jellyfinCustomShowCreator.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.awt.image.BufferedImage

@Serializable
data class EpisodeInfo(
    var title: String = "",
    var number: Int = -1,
    var season: Int = -1,
    var originalFilename: String = "",
    var thumbnail: String = "",
    @Transient var decodedThumbnail: BufferedImage? = null,
) {
    companion object {
        const val ATTACHMENT_THUMBNAIL = "ATTACHMENT"
    }
}