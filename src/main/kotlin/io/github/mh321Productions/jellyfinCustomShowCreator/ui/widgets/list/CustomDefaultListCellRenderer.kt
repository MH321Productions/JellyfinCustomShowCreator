package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.list

import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.JList
import javax.swing.ListCellRenderer

class CustomDefaultListCellRenderer<T>(val customToString: (T) -> String) : ListCellRenderer<T> {

    private val defaultRenderer = DefaultListCellRenderer()

    override fun getListCellRendererComponent(list: JList<out T>, value: T, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component
        = defaultRenderer.getListCellRendererComponent(list, customToString(value), index, isSelected, cellHasFocus)
}