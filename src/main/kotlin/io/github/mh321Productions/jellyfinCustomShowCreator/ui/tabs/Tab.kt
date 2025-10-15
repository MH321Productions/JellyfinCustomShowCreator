package io.github.mh321Productions.jellyfinCustomShowCreator.ui.tabs

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import javax.swing.Icon
import javax.swing.JPanel

abstract class Tab(protected val frame: MainFrame, val title: String, val icon: Icon?) : JPanel()