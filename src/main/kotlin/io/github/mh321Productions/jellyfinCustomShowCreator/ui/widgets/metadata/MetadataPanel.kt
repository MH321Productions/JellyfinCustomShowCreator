package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiEntry
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import net.miginfocom.swing.MigLayout
import javax.swing.*
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.hasAnnotation

class MetadataPanel<TData: Any>(private val frame: MainFrame, data: TData) : JPanel() {

    var data: TData = data
    set(value) {
        field = value
        setProperties()
    }

    init {
        layout = MigLayout("", "[][grow]")
        setProperties()
    }

    private fun setProperties() {
        removeAll()

        data::class.declaredMemberProperties
            .filter { it.hasAnnotation<UiEntry>() && !it.isConst }
            .sortedBy { it.findAnnotation<UiEntry>()!!.index }
            .map { parseType(it) }
            .forEach { addProperty(it) }

        invalidate()
    }

    private fun parseType(it: KProperty1<out TData, *>): PropertyInfo<TData> {
        val annotation = it.findAnnotation<UiEntry>() ?: throw IllegalArgumentException("The property ${it.name} doesn't have the UiEntry annotation")
        val name = annotation.customName.ifEmpty { it.name }
        val formattedName = "$name: "

        return PropertyInfo(formattedName, annotation.type, it as KMutableProperty1<TData, *>)
    }

    private fun addProperty(propertyInfo: PropertyInfo<TData>) {
        add(JLabel(propertyInfo.name))
        add(createComponent(propertyInfo.value, propertyInfo.type), "wrap, grow")
    }

    private fun createComponent(value: KMutableProperty1<TData, *>, type: PropertyType): JComponent {
        return when (type) {
            PropertyType.TextField -> {
                val comp = JTextField(value.get(data) as String)
                comp.document.addDocumentListener(UpdatedDocumentListener(comp, data, value))
                return comp
            }

            PropertyType.TextArea -> {
                val comp = JTextArea(value.get(data) as String)
                comp.document.addDocumentListener(UpdatedDocumentListener(comp, data, value))
                return comp
            }

            PropertyType.Enum -> {
                val enum = value.get(data) as Enum<*>
                val entries = enum::class
                    .functions
                    .first { it.name == "values" }
                    .call() as Array<*>

                val comp = JComboBox(entries)
                comp.isEditable = false
                comp.selectedIndex = enum.ordinal
                comp.addActionListener { value.setter.call(data, entries[comp.selectedIndex]) }

                return comp
            }

            PropertyType.Number -> {
                val comp = JSpinner()

                return comp
            }

            else -> JLabel("<Not implemented>")
        }
    }
}