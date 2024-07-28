package t.me.octopusapps.taskflow.ui.screens.taskflow

import t.me.octopusapps.taskflow.data.local.models.Task

sealed class TaskFlowItem {
    class Tasks(
        val tasks: List<Task>,
        val isCompletedTasksVisible: Boolean
    ) : TaskFlowItem()
    data object Skeleton: TaskFlowItem()
}