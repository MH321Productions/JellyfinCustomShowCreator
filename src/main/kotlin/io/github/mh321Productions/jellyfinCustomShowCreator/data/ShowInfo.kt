package io.github.mh321Productions.jellyfinCustomShowCreator.data

import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiIgnore
import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiUseTextArea
import kotlinx.serialization.Serializable

@Serializable
class ShowInfo {
    var name = "Test"

    @UiUseTextArea
    var description = "Test\nTest"


    @UiIgnore
    val seasons = mutableListOf<SeasonInfo>()
}
