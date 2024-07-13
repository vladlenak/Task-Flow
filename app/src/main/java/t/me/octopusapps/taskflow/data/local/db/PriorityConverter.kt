package t.me.octopusapps.taskflow.data.local.db

import androidx.room.TypeConverter
import t.me.octopusapps.taskflow.data.local.models.Priority

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priorityString: String): Priority {
        return Priority.valueOf(priorityString)
    }
}