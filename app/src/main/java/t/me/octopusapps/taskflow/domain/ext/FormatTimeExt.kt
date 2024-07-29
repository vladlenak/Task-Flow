package t.me.octopusapps.taskflow.domain.ext

import t.me.octopusapps.taskflow.data.remote.CrashlyticsRepository
import t.me.octopusapps.taskflow.domain.constants.CommonConstants
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun String.formatTime(crashlyticsRepository: CrashlyticsRepository): String {
    return try {
        val timeFormatter = DateTimeFormatter.ofPattern(CommonConstants.TIME_PATTERN)
        return LocalTime.parse(this).format(timeFormatter)
    } catch (e: Exception) {
        crashlyticsRepository.sendCrashlytics(e)
        ""
    }
}