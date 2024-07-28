package t.me.octopusapps.taskflow.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import t.me.octopusapps.taskflow.domain.models.Priority
import java.util.UUID

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "task_title") val taskTitle: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "time_spent") var timeSpent: Long = 0L,
    @ColumnInfo(name = "priority") val priority: Priority,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean
)