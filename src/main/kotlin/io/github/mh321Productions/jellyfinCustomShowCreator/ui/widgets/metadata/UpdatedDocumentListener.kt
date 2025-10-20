package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.JTextComponent

class UpdatedDocumentListener(private val comp: JTextComponent, private val update: (String) -> Unit) : DocumentListener {
    override fun insertUpdate(p0: DocumentEvent)  = update(comp.text)
    override fun removeUpdate(p0: DocumentEvent)  = update(comp.text)
    override fun changedUpdate(p0: DocumentEvent) = update(comp.text)
}