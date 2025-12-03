package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.tree

import java.awt.Component
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.TreeCellRenderer

class CustomDefaultTreeCellRenderer<T>(private val customToString: (T) -> String) : TreeCellRenderer {

    private val renderer = DefaultTreeCellRenderer()

    @Suppress("UNCHECKED_CAST")
    override fun getTreeCellRendererComponent(tree: JTree, value: Any, selected: Boolean, expanded: Boolean, leaf: Boolean, row: Int, hasFocus: Boolean): Component {
        val node = value as DefaultMutableTreeNode
        return renderer.getTreeCellRendererComponent(tree, customToString(node.userObject as T), selected, expanded, leaf, row, hasFocus)
    }

}