package io.github.mh321Productions.jellyfinCustomShowCreator.ui.dialogs

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import net.miginfocom.swing.MigLayout
import java.awt.Dimension
import javax.swing.*

class SeasonNumberDialog(frame: MainFrame, oldNumber: Int) : JDialog(frame, "Edit Season Number", true) {

    var newSeasonNumber = oldNumber
        private set

    private val spinner: JSpinner
    private val btnOk: JButton
    private val btnCancel: JButton

    init {
        size = Dimension(250, 125)
        defaultCloseOperation = DISPOSE_ON_CLOSE
        setLocationRelativeTo(frame)

        layout = MigLayout("", "[grow][grow]", "[][][]")

        val lblDescription = JLabel("Select the new number for season $oldNumber")
        add(lblDescription, "cell 0 0 2 1, grow")

        spinner = JSpinner(SpinnerNumberModel(oldNumber, 1, Int.MAX_VALUE, 1))
        spinner.addChangeListener { newSeasonNumber = spinner.value as Int }
        add(spinner, "cell 0 1 2 1, grow")

        btnOk = JButton("OK")
        btnOk.addActionListener { dispose() }
        add(btnOk, "cell 0 2, grow")

        btnCancel = JButton("Cancel")
        btnCancel.addActionListener {
            newSeasonNumber = -1
            dispose()
        }
        add(btnCancel, "cell 1 2, grow")

        isResizable = false
        isVisible = true
    }
}