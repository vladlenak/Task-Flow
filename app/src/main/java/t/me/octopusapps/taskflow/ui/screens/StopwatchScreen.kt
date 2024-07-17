package t.me.octopusapps.taskflow.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.ui.components.getColorByPriority
import t.me.octopusapps.taskflow.ui.dialogs.DeleteTaskDialog
import t.me.octopusapps.taskflow.utilities.TimeFormatHelper

@Composable
fun StopwatchScreen(
    navController: NavHostController,
    task: Task,
    onUpdateTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    var timeElapsed by remember { mutableLongStateOf(task.timeSpent) }
    var isRunning by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(task) {
        task.let {
            timeElapsed = it.timeSpent
        }
    }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000L)
            timeElapsed += 1000L
        }
    }

    BackHandler {
        isRunning = false
        task.timeSpent = timeElapsed
        onUpdateTask(task)
        navController.popBackStack()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 70.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Task: ${task.taskTitle}",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(300.dp)
                .border(
                    2.dp,
                    getColorByPriority(priority = task.priority),
                    CircleShape
                )
                .background(Color.Transparent, CircleShape)
        ) {
            Text(
                text = "Time: ${TimeFormatHelper.getTimeStr(timeElapsed)}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontSize = 20.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { showDialog = true }) {
                    Text("Delete")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = { isRunning = !isRunning }) {
                    Text(if (isRunning) "Pause" else "Start")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(onClick = {
                    isRunning = false
                    task.timeSpent = timeElapsed
                    onUpdateTask(task)
                    navController.popBackStack()
                }) {
                    Text("Stop")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicText(
                text = "Added on: ${task.timestamp}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

    }

    if (showDialog) {
        DeleteTaskDialog(onDismiss = { showDialog = false }) {
            onDeleteTask(task)
            navController.popBackStack()
        }
    }

}