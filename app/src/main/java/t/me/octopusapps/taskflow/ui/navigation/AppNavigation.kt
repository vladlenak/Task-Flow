package t.me.octopusapps.taskflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import t.me.octopusapps.taskflow.domain.constants.NavDestinations
import t.me.octopusapps.taskflow.ui.screens.settings.SettingsScreen
import t.me.octopusapps.taskflow.ui.screens.stopwatch.StopwatchScreen
import t.me.octopusapps.taskflow.ui.screens.taskeditor.TaskEditorScreen
import t.me.octopusapps.taskflow.ui.screens.taskflow.TaskFlowScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavDestinations.TASKS
    ) {
        composable(route = NavDestinations.TASKS) {
            TaskFlowScreen(
                navController = navController
            )
        }
        composable(
            route = "${NavDestinations.STOPWATCH}/{${NavDestinations.TASK_ID_ARG}}",
            arguments = listOf(
                navArgument(NavDestinations.TASK_ID_ARG) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString(NavDestinations.TASK_ID_ARG) ?: ""
            StopwatchScreen(
                navController = navController,
                taskId = taskId
            )
        }
        composable(
            route = "${NavDestinations.EDIT_TASK}/{${NavDestinations.TASK_ID_ARG}}",
            arguments = listOf(
                navArgument(NavDestinations.TASK_ID_ARG) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString(NavDestinations.TASK_ID_ARG) ?: ""
            TaskEditorScreen(
                navController = navController,
                taskId = taskId
            )
        }
        composable(
            route = NavDestinations.SETTINGS
        ) {
            SettingsScreen(
                navController = navController
            )
        }
    }
}