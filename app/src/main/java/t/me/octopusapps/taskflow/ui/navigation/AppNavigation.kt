package t.me.octopusapps.taskflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import t.me.octopusapps.taskflow.domain.constants.NavDestinations
import t.me.octopusapps.taskflow.ui.screens.StopwatchScreen
import t.me.octopusapps.taskflow.ui.screens.TaskEditorScreen
import t.me.octopusapps.taskflow.ui.screens.TasksScreen
import t.me.octopusapps.taskflow.ui.viewmodels.TaskViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    taskViewModel: TaskViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavDestinations.TASKS
    ) {
        composable(route = NavDestinations.TASKS) {
            TasksScreen(
                navController = navController
            )
        }
        composable(
            route = "${NavDestinations.STOPWATCH}/{${NavDestinations.TASK_ID_ARG}}/{${NavDestinations.IS_CLICK_PLAY_ARG}}",
            arguments = listOf(
                navArgument(NavDestinations.TASK_ID_ARG) { type = NavType.IntType },
                navArgument(NavDestinations.IS_CLICK_PLAY_ARG) { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(NavDestinations.TASK_ID_ARG) ?: 0
            val isClickPlay = backStackEntry.arguments?.getBoolean(NavDestinations.IS_CLICK_PLAY_ARG) ?: false
            taskViewModel.getTaskById(taskId)?.let { task ->
                StopwatchScreen(
                    navController = navController,
                    task = task,
                    isClickPlay = isClickPlay,
                    onUpdateTask = { taskViewModel.updateTask(it) },
                    onDeleteTask = { taskViewModel.deleteTask(it) }
                )
            }
        }
        composable(
            route = "${NavDestinations.EDIT_TASK}/{${NavDestinations.TASK_ID_ARG}}",
            arguments = listOf(
                navArgument(NavDestinations.TASK_ID_ARG) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt(NavDestinations.TASK_ID_ARG) ?: 0
            TaskEditorScreen(
                navController = navController,
                taskId = taskId
            )
        }
    }
}