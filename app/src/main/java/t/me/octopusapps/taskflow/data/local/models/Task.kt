package t.me.octopusapps.taskflow.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val taskText: String,
    val timestamp: String,
    var timeSpent: Long = 0L,
    val priority: Priority
)