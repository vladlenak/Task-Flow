package t.me.octopusapps.taskflow.utilities

import t.me.octopusapps.taskflow.data.remote.CrashlyticsRepository
import t.me.octopusapps.taskflow.domain.constants.CommonConstants
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object RelativeDateHelper {
    fun getRelativeDateString(taskDate: String, crashlyticsRepository: CrashlyticsRepository): String {
        return try {
            val dateFormatter = DateTimeFormatter.ofPattern(CommonConstants.DATE_PATTERN)
            val taskLocalDate = LocalDate.parse(taskDate, dateFormatter)
            val today = LocalDate.now()

            when {
                taskLocalDate.isEqual(today) -> "Today"
                taskLocalDate.isEqual(today.minusDays(1)) -> "Yesterday"
                taskLocalDate.isEqual(today.plusDays(1)) -> "Tomorrow"
                else -> taskDate
            }
        } catch (e: Exception) {
            crashlyticsRepository.sendCrashlytics(e)
            ""
        }
    }
}