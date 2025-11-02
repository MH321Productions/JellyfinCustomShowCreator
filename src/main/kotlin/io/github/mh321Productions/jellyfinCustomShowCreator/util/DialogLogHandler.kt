package io.github.mh321Productions.jellyfinCustomShowCreator.util

import io.github.mh321Productions.jellyfinCustomShowCreator.ui.MainFrame
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord
import javax.swing.JOptionPane

class DialogLogHandler: Handler() {

    var frame: MainFrame? = null

    override fun publish(record: LogRecord) {
        if (record.level.intValue() < Level.INFO.intValue()) return

        val title = record.loggerName
        val message = if (record.thrown != null) "${record.message}\n${formatException(record.thrown)}" else record.message
        val messageType = getMessageType(record.level)

        JOptionPane.showMessageDialog(frame, message, title, messageType)
    }

    private fun getMessageType(level: Level): Int = when (level) {
        Level.INFO -> JOptionPane.INFORMATION_MESSAGE
        Level.WARNING -> JOptionPane.WARNING_MESSAGE
        Level.SEVERE -> JOptionPane.ERROR_MESSAGE
        else -> JOptionPane.PLAIN_MESSAGE
    }

    private fun formatException(e: Throwable) = "${e::class.simpleName}: ${e.message}"

    override fun flush() = Unit
    override fun close() = Unit
}