package t.me.octopusapps.taskflow.utilities

import t.me.octopusapps.taskflow.data.remote.CrashlyticsRepository

object TimeFormatHelper {

    fun getTimeStr(timeElapsed: Long, crashlyticsRepository: CrashlyticsRepository): String {
        return try {
            val seconds = timeElapsed / 1000
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val remainingSeconds = seconds % 60

            when {
                hours > 0 -> "${hours}h ${minutes}m ${remainingSeconds}s"
                minutes > 0 -> "${minutes}m ${remainingSeconds}s"
                else -> "${remainingSeconds}s"
            }
        } catch (e: Exception) {
            crashlyticsRepository.sendCrashlytics(e)
            ""
        }
    }

}