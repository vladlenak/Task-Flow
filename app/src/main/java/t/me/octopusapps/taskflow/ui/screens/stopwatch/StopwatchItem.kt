package t.me.octopusapps.taskflow.ui.screens.stopwatch

import t.me.octopusapps.taskflow.data.local.models.Task

sealed class StopwatchItem {
    class Stopwatch(
        val task: Task
    ) : StopwatchItem()

    data object Skeleton: StopwatchItem()
}