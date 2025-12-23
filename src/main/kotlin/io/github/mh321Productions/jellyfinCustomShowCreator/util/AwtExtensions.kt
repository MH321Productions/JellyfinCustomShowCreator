package io.github.mh321Productions.jellyfinCustomShowCreator.util

import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle

operator fun Rectangle.component1() = x
operator fun Rectangle.component2() = y
operator fun Rectangle.component3() = width
operator fun Rectangle.component4() = height

operator fun Point.component1() = x
operator fun Point.component2() = y

operator fun Dimension.component1() = width
operator fun Dimension.component2() = height