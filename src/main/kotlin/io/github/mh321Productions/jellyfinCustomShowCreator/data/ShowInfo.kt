package io.github.mh321Productions.jellyfinCustomShowCreator.data

import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiIgnore
import kotlinx.serialization.Serializable

@Serializable
class ShowInfo {
    var name = ""


    @UiIgnore
    val seasons = mutableListOf<SeasonInfo>()
}
