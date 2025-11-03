package io.github.mh321Productions.jellyfinCustomShowCreator.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoInfo(
    val streams: List<StreamInfo>,
    val format: FormatInfo
)

@Serializable
data class StreamInfo(
    val index: Int,
    @SerialName("codec_type") val type: CodecType,
    val tags: StreamTagsInfo? = null,
)

@Serializable
data class FormatInfo(
    val filename: String,
    val duration: Double,
    val tags: FormatTagsInfo? = null
)

@Serializable
data class FormatTagsInfo(
    val title: String? = null,
    @SerialName("COMMENT") val comment: String? = null,
    @SerialName("ARTIST") val artist: String? = null,
    @SerialName("DATE") val date: String? = null,
    @SerialName("DESCRIPTION") val description: String? = null,
    @SerialName("SYNOPSIS") val synopsis: String? = null,
    @SerialName("PURL") val purl: String? = null,
    @SerialName("ENCODER") val encoder: String? = null
)

@Serializable
data class StreamTagsInfo(
    val language: String? = null,
    val filename: String? = null,
    val mimetype: String? = null,
)

@Serializable
enum class CodecType {
    @SerialName("video") Video,
    @SerialName("audio") Audio,
    @SerialName("subtitle") Subtitle,
    @SerialName("attachment") Attachment
}