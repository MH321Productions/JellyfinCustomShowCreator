package io.github.mh321Productions.jellyfinCustomShowCreator.tests.laf

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.Color
import javax.swing.UIManager
import kotlin.test.Test


class LafColorTests {

    @Test
    fun `List all available keys`() {
        // Arrange
        setupLaf()

        // Act
        val defaults = UIManager.getLookAndFeelDefaults()
        val keys = defaults
            .keys
            .filter { defaults[it] != null }
            .associateWith { defaults[it] }

        keys.forEach { (k, v) -> println("$k: $v") }
    }

    @Test
    fun `List all available colors`() {
        // Arrange
        setupLaf()

        // Act
        val defaults = UIManager.getLookAndFeelDefaults()
        val keys = defaults
            .keys
            .filter { defaults.getColor(it) != null }
            .associateWith { defaults.getColor(it) }

        keys.forEach { (k, v) -> println("$k: $v") }
    }

    @Test
    fun `The border color has to be present in the default colors`() {
        // Arrange
        setupLaf()

        // Act
        val defaults = UIManager.getLookAndFeelDefaults()
        val keys = defaults
            .keys
            .filter {
                val color = defaults.getColor(it)
                color != null && color.rgb == panelBorderColor.rgb
            }
            .associateWith { defaults.getColor(it) }

        keys.forEach { (k, v) -> println("$k: $v") }

        // Assert
        assert(keys.isNotEmpty()) { "The border color has to be present in the default colors" }
    }

    private fun setupLaf() {
        FlatDarkLaf.setup()
    }

    companion object {
        val panelBorderColor = Color(97, 99, 101)
    }
}