package io.github.mh321Productions.jellyfinCustomShowCreator.ui

import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLaf
import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.extras.FlatAnimatedLafChange
import java.awt.Color
import javax.swing.UIManager
import javax.swing.border.LineBorder
import javax.swing.border.TitledBorder

object ThemeUtils {
    fun setTheme(isDark: Boolean) {
        FlatAnimatedLafChange.showSnapshot()
        if (isDark) {
            FlatDarkLaf.setup()
        } else {
            FlatLightLaf.setup()
        }
        FlatLaf.updateUI()
        FlatAnimatedLafChange.hideSnapshotWithAnimation()
    }

    fun getBorderColor(): Color = UIManager.getColor("Component.borderColor")

    fun createLineBorder() = LineBorder(getBorderColor())
    fun createTitledBorder(title: String) = TitledBorder(createLineBorder(), title, TitledBorder.LEADING, TitledBorder.TOP)
}