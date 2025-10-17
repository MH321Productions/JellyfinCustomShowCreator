package io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.PropertyType

@Target(AnnotationTarget.PROPERTY)
annotation class UiEntry(val index: Int, val type: PropertyType, val customName: String = "")
