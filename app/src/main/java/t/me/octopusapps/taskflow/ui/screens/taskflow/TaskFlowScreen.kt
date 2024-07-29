package t.me.octopusapps.taskflow.ui.screens.taskflow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import t.me.octopusapps.taskflow.domain.constants.NavDestinations
import t.me.octopusapps.taskflow.ui.components.TaskListComponent
import t.me.octopusapps.taskflow.ui.dialogs.TaskCreatorDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFlowScreen(
    navController: NavHostController,
    viewModel: TaskFlowViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    val viewState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshTasks()
    }

    when (val tasksItem = viewState.value.tasksItem) {
        TaskFlowItem.Skeleton -> {}
        is TaskFlowItem.Tasks -> {
            Scaffold(
                topBar = {
                    androidx.compose.material3.TopAppBar(
                        title = { Text("Task Flow") },
                        actions = {
                            IconButton(onClick = {
                                navController.navigate(NavDestinations.SETTINGS)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
                    }
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TaskListComponent(
                            tasks = tasksItem.tasks,
                            isCompletedTasksVisible = tasksItem.isCompletedTasksVisible,
                            navController = navController,
                            modifier = Modifier.weight(1f),
                            viewModel = viewModel
                        )
                        if (showDialog) {
                            TaskCreatorDialog(
                                crashlyticsRepository = viewModel.getCrashlyticsRepository(),
                                onDismiss = { showDialog = false },
                                onAddTask = { taskText, priority, selectedDate, selectedTime ->
                                    viewModel.addTask(
                                        taskText,
                                        priority,
                                        selectedDate,
                                        selectedTime
                                    )
                                    showDialog = false
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}