package t.me.octopusapps.taskflow.ui.ext

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun String.formatTime(): String {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    return LocalTime.parse(this).format(timeFormatter)
}