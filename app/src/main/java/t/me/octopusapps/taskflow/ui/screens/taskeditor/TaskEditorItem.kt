package t.me.octopusapps.taskflow.ui.screens.taskeditor

import t.me.octopusapps.taskflow.data.local.models.Task

sealed class TaskEditorItem {
    class TaskEditor(
        val task: Task
    ): TaskEditorItem()

    data object Skeleton: TaskEditorItem()
}