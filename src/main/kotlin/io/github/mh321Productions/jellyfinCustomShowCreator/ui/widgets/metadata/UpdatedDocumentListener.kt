package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.JTextComponent
import kotlin.reflect.KMutableProperty1

class UpdatedDocumentListener<TData>(private val comp: JTextComponent, private val data: TData, private val value: KMutableProperty1<out TData, *>) : DocumentListener {
    override fun insertUpdate(p0: DocumentEvent) = update()
    override fun removeUpdate(p0: DocumentEvent) = update()
    override fun changedUpdate(p0: DocumentEvent) = update()

    private fun update() {
        value.setter.call(data, comp.text)
    }
}