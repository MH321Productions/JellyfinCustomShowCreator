package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiIgnore
import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiName
import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiUseTextArea
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import net.miginfocom.swing.MigLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.typeOf

class MetadataPanel<TData: Any>(private val frame: MainFrame, data: TData) : JPanel() {

    var data: TData = data
    set(value) {
        field = value
        setProperties()
    }

    init {
        layout = MigLayout()
        setProperties()
    }

    private fun setProperties() {
        removeAll()

        data::class.declaredMemberProperties
            .filter { !it.hasAnnotation<UiIgnore>() && !it.isConst }
            .map(::parseType)
            .forEach { addProperty(it) }

        invalidate()
    }

    private fun addProperty(propertyInfo: PropertyInfo<TData>) {
        add(JLabel(propertyInfo.name))
        add(createComponent(propertyInfo.value, propertyInfo.type), "grow, wrap")
    }

    private fun parseType(it: KProperty1<out TData, *>): PropertyInfo<TData> {
        val annotation = it.findAnnotation<UiName>()
        val name = annotation?.name ?: it.name
        val useTextArea = it.hasAnnotation<UiUseTextArea>()
        val type = when (it.returnType) {
            typeOf<String>() -> if (useTextArea) PropertyType.TextArea else PropertyType.TextField
            typeOf<Number>() -> PropertyType.Number
            typeOf<Boolean>() -> PropertyType.Checkbox
            typeOf<Enum<*>>() -> PropertyType.Enum
            else -> throw IllegalArgumentException("Unsupported property type ${it.returnType} for property ${it.name}")
        }

        return PropertyInfo(name, type, it as KMutableProperty1<out TData, *>)
    }

    private fun createComponent(value: KMutableProperty1<out TData, *>, type: PropertyType): JComponent {
        return when (type) {
            PropertyType.TextField -> {
                val comp = JTextField(value.getter.call(data) as String)
                comp.document.addDocumentListener(UpdatedDocumentListener(comp, data, value))
                return comp
            }

            PropertyType.TextArea -> {
                val comp = JTextArea(value.getter.call(data) as String)
                comp.document.addDocumentListener(UpdatedDocumentListener(comp, data, value))
                return comp
            }

            else -> JLabel("<Not implemented>")
        }
    }
}