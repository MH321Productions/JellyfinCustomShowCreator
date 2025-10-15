package io.github.mh321Productions.jellyfinCustomShowCreator.ui.widgets.metadata

import io.github.mh321Productions.jellyfinCustomShowCreator.data.annotations.UiIgnore
import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import net.miginfocom.swing.MigLayout
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

class MetadataPanel<TData: Any>(private val frame: MainFrame, var data: TData) : JPanel() {

    init {
        layout = MigLayout()

        data::class.declaredMemberProperties
            .filter { it.findAnnotation<UiIgnore>() == null }
            .forEach { add(JLabel("${it.name} (${it.returnType})"), "wrap") }
    }
}