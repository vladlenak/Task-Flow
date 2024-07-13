package t.me.octopusapps.taskflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.ui.components.TaskItem
import t.me.octopusapps.taskflow.ui.dialogs.TaskCreatorDialog

@Composable
fun TasksScreen(
    navController: NavHostController,
    localeTasks: StateFlow<List<Task>>,
    onAddTask: (String) -> Unit
) {
    val tasks by localeTasks.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
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
                Text(
                    text = "Task Flow",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 40.dp)
                )

                if (tasks.isEmpty()) {
                    EmptyTaskMessage()
                } else {
                    if (tasks.size == 1) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.padding(top = 16.dp))
                            OneTaskMessage()
                            TaskList(
                                tasks = tasks,
                                navController = navController,
                                modifier = Modifier.weight(1f)
                            )
                        }

                    } else {
                        TaskList(
                            tasks = tasks,
                            navController = navController,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                if (showDialog) {
                    TaskCreatorDialog(
                        onDismiss = { showDialog = false },
                        onAddTask = { taskText ->
                            onAddTask(taskText)
                            showDialog = false
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun TaskList(
    tasks: List<Task>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { task ->
            TaskItem(task = task, onClick = {
                navController.navigate("stopwatch/${task.id}")
            })
        }
    }
}

@Composable
fun EmptyTaskMessage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Text(
            text = "Task list is empty",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
        Text(
            text = "Click here to create your first task",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 100.dp, end = 32.dp)
        )
    }
}

@Composable
fun OneTaskMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Text(
            text = "Click on task to start",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
