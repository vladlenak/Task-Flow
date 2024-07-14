package t.me.octopusapps.taskflow.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import t.me.octopusapps.taskflow.ui.screens.StopwatchScreen
import t.me.octopusapps.taskflow.ui.screens.TasksScreen
import t.me.octopusapps.taskflow.ui.theme.TaskFlowTheme
import t.me.octopusapps.taskflow.ui.viewmodels.StopwatchViewModel
import t.me.octopusapps.taskflow.ui.viewmodels.TaskViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TaskFlowApp()
        }
    }
}

@Composable
fun TaskFlowApp() {
    TaskFlowTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val taskViewModel: TaskViewModel = viewModel()
            val stopwatchViewModel: StopwatchViewModel = viewModel()

            NavHost(navController, startDestination = "tasks") {
                composable(route = "tasks") { TasksScreen(navController, taskViewModel) }
                composable(
                    route = "stopwatch/{taskId}",
                    arguments = listOf(navArgument("taskId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0

                    stopwatchViewModel.getTaskById(taskId)?.let { localTask ->
                        StopwatchScreen(
                            navController = navController,
                            task = localTask,
                            viewModel = stopwatchViewModel
                        )
                    }

                }
            }
        }
    }
}