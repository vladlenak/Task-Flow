package t.me.octopusapps.taskflow.domain.ext

import t.me.octopusapps.taskflow.domain.constants.CommonConstants
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun String.formatTime(): String {
    val timeFormatter = DateTimeFormatter.ofPattern(CommonConstants.TIME_PATTERN)
    return LocalTime.parse(this).format(timeFormatter)
}