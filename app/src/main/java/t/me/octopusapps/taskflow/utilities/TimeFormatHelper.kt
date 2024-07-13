package t.me.octopusapps.taskflow.utilities

object TimeFormatHelper {

    fun getTimeStr(timeElapsed: Long): String {
        val seconds = timeElapsed / 1000
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        return when {
            hours > 0 -> "${hours}h ${minutes}m ${remainingSeconds}s"
            minutes > 0 -> "${minutes}m ${remainingSeconds}s"
            else -> "${remainingSeconds}s"
        }
    }

}