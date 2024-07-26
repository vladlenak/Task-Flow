package t.me.octopusapps.taskflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import t.me.octopusapps.taskflow.ui.viewmodels.TaskViewModel

@Composable
fun TaskListComponent(
    tasks: List<Task>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel
) {
    val sortedTasksWithHeaders = mutableListOf<Any>()
    Priority.entries.forEachIndexed { index, priority ->
        val tasksForPriority = tasks.filter { it.priority == priority }
        if (tasksForPriority.isNotEmpty()) {
            sortedTasksWithHeaders.add(FullPriority.entries[index])
            sortedTasksWithHeaders.addAll(tasksForPriority.sortedWith(compareBy({ it.date }, { it.time })))
        }
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