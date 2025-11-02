package io.github.mh321Productions.jellyfinCustomShowCreator.util

import java.util.Calendar
import java.util.Date

fun Int.toDate(calendarField: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = 0
    calendar.set(calendarField, this)
    return calendar.time
}

operator fun Date.get(calendarField: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(calendarField)
}