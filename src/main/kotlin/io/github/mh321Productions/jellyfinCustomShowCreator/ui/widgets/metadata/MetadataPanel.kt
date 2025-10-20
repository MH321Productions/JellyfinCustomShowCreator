package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata.extractors.DataExtractor
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import net.miginfocom.swing.MigLayout
import javax.swing.JLabel
import javax.swing.JPanel

class MetadataPanel<TData: Any>(private val frame: MainFrame, data: TData, private val extractor: DataExtractor<TData>) : JPanel() {

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

        extractor.getWidgets(data)
            .forEach(::addProperty)

        invalidate()
    }

    private fun addProperty(propertyInfo: PropertyInfo) {
        add(JLabel(propertyInfo.name), "right")
        add(propertyInfo.comp, "wrap, grow")
    }
}