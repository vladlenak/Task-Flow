package t.me.octopusapps.taskflow.ui.screens.stopwatch

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import t.me.octopusapps.taskflow.domain.constants.NavDestinations
import t.me.octopusapps.taskflow.ui.components.DisplayDateTime
import t.me.octopusapps.taskflow.ui.components.getColorByPriority
import t.me.octopusapps.taskflow.ui.dialogs.DeleteTaskDialog
import t.me.octopusapps.taskflow.utilities.TimeFormatHelper

@Composable
fun StopwatchScreen(
    navController: NavHostController,
    taskId: String,
    isClickPlay: Boolean,
    viewModel: StopwatchViewModel = hiltViewModel()
) {

    val viewState = viewModel.usState.collectAsState()
    viewModel.loadTask(taskId)

    when(val stopwatchItem = viewState.value.stopwatchItem) {
        StopwatchItem.Skeleton -> {}
        is StopwatchItem.Stopwatch -> {
            var timeElapsed by remember { mutableLongStateOf(stopwatchItem.task.timeSpent) }
            var isRunning by remember { mutableStateOf(isClickPlay) }
            var showDialog by remember { mutableStateOf(false) }
            var isChecked by remember { mutableStateOf(stopwatchItem.task.isCompleted) }

            LaunchedEffect(stopwatchItem.task) {
                stopwatchItem.task.let {
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
                stopwatchItem.task.timeSpent = timeElapsed
                viewModel.updateTask(stopwatchItem.task.copy(isCompleted = isChecked))
                navController.popBackStack()
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 70.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = { checked ->
                            isChecked = checked
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(48.dp)
                    )
                    Text(
                        text = "Task: ${stopwatchItem.task.taskTitle}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            navController.navigate("${NavDestinations.EDIT_TASK}/${stopwatchItem.task.id}") {
                                popUpTo("${NavDestinations.STOPWATCH}/{${NavDestinations.TASK_ID_ARG}}/{${NavDestinations.IS_CLICK_PLAY_ARG}}") { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Task Action",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(300.dp)
                        .border(
                            2.dp,
                            getColorByPriority(priority = stopwatchItem.task.priority),
                            CircleShape
                        )
                        .background(Color.Transparent, CircleShape)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Time: ${TimeFormatHelper.getTimeStr(timeElapsed)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp
                        )
                        DisplayDateTime(task = stopwatchItem.task)
                    }
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
                            stopwatchItem.task.timeSpent = timeElapsed
                            viewModel.updateTask(stopwatchItem.task.copy(isCompleted = isChecked))
                            navController.popBackStack()
                        }) {
                            Text("Stop")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Added on: ${stopwatchItem.task.timestamp}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            if (showDialog) {
                DeleteTaskDialog(onDismiss = { showDialog = false }) {
                    viewModel.deleteTask(stopwatchItem.task)
                    navController.popBackStack()
                }
            }
        }
    }


}