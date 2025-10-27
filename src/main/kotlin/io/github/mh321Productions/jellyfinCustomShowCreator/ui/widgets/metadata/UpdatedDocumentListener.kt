package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.JTextComponent

class UpdatedDocumentListener(private val frame: MainFrame, private val comp: JTextComponent, private val update: (String) -> Unit) : DocumentListener {
    override fun insertUpdate(p0: DocumentEvent)  = updateAndMarkDirty(comp.text)
    override fun removeUpdate(p0: DocumentEvent)  = updateAndMarkDirty(comp.text)
    override fun changedUpdate(p0: DocumentEvent) = updateAndMarkDirty(comp.text)

    private fun updateAndMarkDirty(text: String) {
        update(text)
        frame.markDirty()
    }
}