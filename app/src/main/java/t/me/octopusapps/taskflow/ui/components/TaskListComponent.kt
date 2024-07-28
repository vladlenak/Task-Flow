package t.me.octopusapps.taskflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.domain.constants.NavDestinations
import t.me.octopusapps.taskflow.domain.models.FullPriority
import t.me.octopusapps.taskflow.domain.models.Priority
import t.me.octopusapps.taskflow.ui.screens.taskflow.TaskFlowViewModel

@Composable
fun TaskListComponent(
    tasks: List<Task>,
    isCompletedTasksVisible: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    taskViewModel: TaskFlowViewModel
) {
    val sortedTasksWithHeaders = mutableListOf<Any>()
    val filteredTasks = if (isCompletedTasksVisible) {
        tasks.filter { !it.isCompleted }
    } else {
        tasks
    }

    Priority.entries.forEachIndexed { index, priority ->
        val tasksForPriority = filteredTasks.filter { it.priority == priority }
        if (tasksForPriority.isNotEmpty()) {
            sortedTasksWithHeaders.add(FullPriority.entries[index])
            sortedTasksWithHeaders.addAll(tasksForPriority.sortedWith(compareBy({ it.date }, { it.time })))
        }
    }

    if (sortedTasksWithHeaders.isEmpty()) {
        EmptyTaskMessageComponent()
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (sortedTasksWithHeaders.size == 2) {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                OneTaskMessageComponent()
            }

            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sortedTasksWithHeaders) { item ->
                    when (item) {
                        is FullPriority -> PriorityHeader(priority = item)
                        is Task -> TaskItem(
                            task = item,
                            onClickItem = {
                                navController.navigate("${NavDestinations.STOPWATCH}/${item.id}/false")
                            },
                            onCheckedChange = { task, isChecked ->
                                taskViewModel.onCheckedChange(task, isChecked)
                            },
                            onClickPlay = {
                                navController.navigate("${NavDestinations.STOPWATCH}/${item.id}/true")
                            }
                        )
                    }
                }
            }
        }
    }

}