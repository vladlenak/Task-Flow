package t.me.octopusapps.taskflow.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "task_title") val taskTitle: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "time_spent") var timeSpent: Long = 0L,
    @ColumnInfo(name = "priority") val priority: Priority
)