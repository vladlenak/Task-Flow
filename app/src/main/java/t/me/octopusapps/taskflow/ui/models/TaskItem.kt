package t.me.octopusapps.taskflow.ui.models

import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.domain.models.Priority

sealed class TaskItem {
    class Task(
        val id: Int,
        val taskTitle: String,
        val timestamp: String,
        var timeSpent: Long = 0L,
        val priority: Priority,
        val date: String,
        val time: String,
        val isCompleted: Boolean
    ): TaskItem()

    data object Skeleton: TaskItem()
}

fun Task.mapToView() = TaskItem.Task(
    id = id,
    taskTitle = taskTitle,
    timestamp = timestamp,
    timeSpent = timeSpent,
    priority = priority,
    date = date,
    time = time,
    isCompleted = isCompleted
)

fun TaskItem.Task.mapToEntity() = Task(
    id = id,
    taskTitle = taskTitle,
    timeSpent = timeSpent,
    timestamp = timestamp,
    priority = priority,
    date = date,
    time = time,
    isCompleted = isCompleted
)