package io.github.mh321Productions.jellyfinCustomShowCreator.ui

import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLaf
import com.formdev.flatlaf.FlatLightLaf
import com.formdev.flatlaf.extras.FlatAnimatedLafChange

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
}