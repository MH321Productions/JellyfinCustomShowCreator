package io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations

/**
 * Instruct the Ui Editor to use the given custom name
 * instead of parsing the property name
 */
@Target(AnnotationTarget.PROPERTY)
annotation class UiName(val name: String)
