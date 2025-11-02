package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.PropertyInfo
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.UpdatedDocumentListener
import io.github.mh321Productions.jellyfinCustomShowCreator.util.get
import io.github.mh321Productions.jellyfinCustomShowCreator.util.toDate
import java.util.Date
import javax.swing.*

abstract class DataExtractor<T>(protected val frame: MainFrame) {
    abstract fun getWidgets(data: T): List<PropertyInfo>

    protected fun textField(name: String, initialValue: String, updateListener: (String) -> Unit): PropertyInfo {
        val comp = JTextField(initialValue)
        comp.document.addDocumentListener(UpdatedDocumentListener(frame, comp, updateListener))
        return PropertyInfo(name, comp)
    }

    protected fun textArea(name: String, initialValue: String, updateListener: (String) -> Unit): PropertyInfo {
        val comp = JTextArea(initialValue)
        comp.document.addDocumentListener(UpdatedDocumentListener(frame, comp, updateListener))
        return PropertyInfo(name, comp)
    }

    protected fun numberSpinner(name: String, initialValue: Number, min: Number, max: Number, step: Number, decimalFormat: String, updateListener: (Number) -> Unit)
        = spinner(
            name,
            SpinnerNumberModel(initialValue, min as Comparable<*>, max as Comparable<*>, step),
            { JSpinner.NumberEditor(it, decimalFormat) },
            updateListener
        )

    protected fun dateSpinner(name: String, initialValue: Int, min: Int, max: Int, calendarField: Int, dateFormat: String, updateListener: (Int) -> Unit)
        = spinner(
            name,
            SpinnerDateModel(initialValue.toDate(calendarField), min.toDate(calendarField), max.toDate(calendarField), calendarField),
            { JSpinner.DateEditor(it, dateFormat) },
            dateUpdateListener(calendarField, updateListener)
        )

    @Suppress("UNCHECKED_CAST")
    protected fun <T> spinner(name: String, model: SpinnerModel, editor: (JSpinner) -> JComponent, updateListener: (T) -> Unit): PropertyInfo {
        val comp = JSpinner(model)
        comp.addChangeListener {
            updateListener(comp.value as T)
            frame.markDirty()
        }
        comp.editor = editor(comp)
        return PropertyInfo(name, comp)
    }

    protected inline fun <reified T: Enum<T>> comboBox(name: String, initialValue: T, noinline updateListener: (T) -> Unit) = comboBox(name, enumValues<T>(), initialValue, updateListener)

    protected fun <T> comboBox(name: String, values: Array<T>, initialValue: T, updateListener: (T) -> Unit): PropertyInfo {
        val comp = JComboBox(values)
        comp.selectedItem = initialValue
        comp.addActionListener {
            updateListener(values[comp.selectedIndex])
            frame.markDirty()
        }
        return PropertyInfo(name, comp)
    }

    private fun dateUpdateListener(calendarField: Int, updateListener: (Int) -> Unit): (Date) -> Unit = { updateListener(it[calendarField]) }
}