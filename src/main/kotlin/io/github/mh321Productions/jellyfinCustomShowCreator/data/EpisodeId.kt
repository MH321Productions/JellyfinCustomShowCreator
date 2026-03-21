package io.github.mh321Productions.jellyfinCustomShowCreator.data

import io.github.mh321Productions.jellyfinCustomShowCreator.util.getEpisodeOrNull
import io.github.mh321Productions.jellyfinCustomShowCreator.util.getSeason
import java.awt.datatransfer.DataFlavor

data class EpisodeId(val season: Int, val episode: Int) {

    companion object {
        val dataFlavor = DataFlavor(EpisodeId::class.java, "Episode Id")

        fun fromEpisode(show: ShowInfo, episode: EpisodeInfo): EpisodeId {
            val unassignedIndex = show.unassignedEpisodes.indexOf(episode)
            if (unassignedIndex != -1) return EpisodeId(-1, unassignedIndex)

            val season = show.seasons.single { it.episodes.contains(episode) }
            return EpisodeId(season.number, episode.number)
        }
    }

    val isSeasonHeader = episode == -1
    val isEpisode = !isSeasonHeader
    val isSpecial = season == 0
    val isNormal = season > 0
    val isAssigned = season != -1
    val isUnassigned = !isAssigned

    fun getName(show: ShowInfo) = when (episode) {
        -1 -> {
            if (season != -1 && show.seasons.count { it.number == season } != 1) throw IllegalArgumentException("No season $season found in show")

            when (season) {
                -1 -> "Unassigned Episodes"
                0 -> "Specials"
                else -> "Season $season"
            }
        }
        else -> when (season) {
            -1 -> show.unassignedEpisodes.getOrNull(episode)?.title
                ?: throw IllegalArgumentException("No unassigned episode $episode found")
            else -> show.seasons.getSeason(season).episodes.getEpisodeOrNull(episode)?.title
                ?: throw IllegalArgumentException("No episode $episode found in season $season")
        }
    }

    override fun toString() = "Episode Id ($season, $episode)"
}