package io.github.mh321Productions.jellyfinCustomShowCreator.util

import java.io.File

fun File.isRelativeTo(rootDir: File) = relativeToOrNull(rootDir) != null

fun String.relativeFile(rootDir: File?) = File(rootDir, this)