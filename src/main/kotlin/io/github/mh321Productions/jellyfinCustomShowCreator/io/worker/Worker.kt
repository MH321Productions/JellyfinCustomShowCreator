package io.github.mh321Productions.jellyfinCustomShowCreator.io.worker

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import javax.swing.JProgressBar

interface Worker {
    suspend fun doWork(frame: MainFrame, progressBar: JProgressBar)
}