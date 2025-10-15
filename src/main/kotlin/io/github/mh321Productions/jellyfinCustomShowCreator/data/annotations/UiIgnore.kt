package io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations

import io.github.mh321Productions.jellyfinCustomShowCreator.data.ShowInfo

/**
 * Excludes properties in the info classes from the Ui editor
 * @see ShowInfo
 */
@Target(AnnotationTarget.PROPERTY)
annotation class UiIgnore()
