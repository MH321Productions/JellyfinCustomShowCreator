package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import kotlin.reflect.KMutableProperty1

data class PropertyInfo<TData>(val name: String, val type: PropertyType, val value: KMutableProperty1<TData, *>)
