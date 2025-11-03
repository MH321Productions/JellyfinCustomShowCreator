package io.github.mh321Productions.jellyfinCustomShowCreator.io.worker

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame

interface Worker {
    suspend fun doWork(frame: MainFrame)
}