package io.github.mh321Productions.jellyfinCustomShowCreator.util

import io.github.mh321Productions.jellyfinCustomShowCreator.data.EpisodeInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.data.SeasonInfo

fun MutableList<SeasonInfo>.getSeason(seasonIndex: Int) = first { it.number == seasonIndex }
fun MutableList<SeasonInfo>.getSeasonOrNull(seasonIndex: Int) = firstOrNull { it.number == seasonIndex }

fun MutableList<EpisodeInfo>.getEpisode(episodeIndex: Int) = first { it.number == episodeIndex }
fun MutableList<EpisodeInfo>.getEpisodeOrNull(episodeIndex: Int) = firstOrNull { it.number == episodeIndex }