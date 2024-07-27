package t.me.octopusapps.taskflow.ui.contract

import t.me.octopusapps.taskflow.ui.models.TaskItem

data class TaskEditorState(
    val task: TaskItem = TaskItem.Skeleton
)