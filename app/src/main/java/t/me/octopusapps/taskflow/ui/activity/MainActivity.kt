package t.me.octopusapps.taskflow.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import t.me.octopusapps.taskflow.ui.navigation.AppNavigation
import t.me.octopusapps.taskflow.ui.theme.TaskFlowTheme
import t.me.octopusapps.taskflow.ui.viewmodels.TaskViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val taskViewModel: TaskViewModel = viewModel()
            TaskFlowTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        taskViewModel = taskViewModel
                    )
                }
            }
        }
    }
}