package t.me.octopusapps.taskflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import t.me.octopusapps.taskflow.ui.screens.StopwatchScreen
import t.me.octopusapps.taskflow.ui.screens.TasksScreen
import t.me.octopusapps.taskflow.ui.viewmodels.TaskViewModel

@Composable
fun AppNavigation(navController: NavHostController, taskViewModel: TaskViewModel) {
    NavHost(
        navController = navController,
        startDestination = NavDestinations.TASKS
    ) {
        composable(route = NavDestinations.TASKS) {
            TasksScreen(
                navController = navController,
                viewModel = taskViewModel
            )
        }
        composable(
            route = "${NavDestinations.STOPWATCH}/{${NavDestinations.TASK_ID_ARG}}",
            arguments = listOf(navArgument(NavDestinations.TASK_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(NavDestinations.TASK_ID_ARG) ?: 0
            taskViewModel.getTaskById(taskId)?.let { task ->
                StopwatchScreen(
                    navController = navController,
                    task = task,
                    onUpdateTask = { taskViewModel.updateTask(it) },
                    onDeleteTask = { taskViewModel.deleteTask(it) }
                )
            }
        }
    }
}