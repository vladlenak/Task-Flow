package t.me.octopusapps.taskflow.ui.screens

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import t.me.octopusapps.taskflow.domain.ext.formatTime
import t.me.octopusapps.taskflow.domain.models.Priority
import t.me.octopusapps.taskflow.ui.components.getColorByPriority
import t.me.octopusapps.taskflow.ui.models.TaskItem
import t.me.octopusapps.taskflow.ui.models.mapToEntity
import t.me.octopusapps.taskflow.ui.viewmodels.TaskEditorViewModel
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorScreen(
    navController: NavHostController,
    taskId: Int,
    viewModel: TaskEditorViewModel = hiltViewModel()
) {

    val viewState = viewModel.usState.collectAsState()
    val context = LocalContext.current
    val priorities = Priority.entries.toTypedArray()

    viewModel.loadTask(taskId)

    when (val task = viewState.value.task) {
        TaskItem.Skeleton -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Text(
                    text = "Task is empty",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is TaskItem.Task -> {
            var taskText by remember { mutableStateOf(TextFieldValue(task.taskTitle)) }
            var selectedPriority by remember { mutableStateOf(task.priority) }
            var selectedDate by remember { mutableStateOf(LocalDate.parse(task.date)) }
            var selectedTime by remember { mutableStateOf(LocalTime.parse(task.time)) }
            var showDatePicker by remember { mutableStateOf(false) }
            var showTimePicker by remember { mutableStateOf(false) }

            if (showDatePicker) {
                LaunchedEffect(Unit) {
                    val calendar = Calendar.getInstance()
                    android.app.DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                            showDatePicker = false
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }

            if (showTimePicker) {
                LaunchedEffect(Unit) {
                    TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            selectedTime = LocalTime.of(hourOfDay, minute)
                            showTimePicker = false
                        },
                        selectedTime.hour,
                        selectedTime.minute,
                        true
                    ).show()
                }
            }

            Scaffold(
                topBar = {
                    androidx.compose.material3.TopAppBar(
                        title = { Text("Edit Task") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            value = taskText,
                            onValueChange = {
                                taskText = it
                            },
                            label = { Text("Task Title") }
                        )
                        Text("Select Priority")
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            priorities.forEach { priority ->
                                Button(
                                    onClick = { selectedPriority = priority },
                                    colors = if (selectedPriority == priority) {
                                        ButtonDefaults.buttonColors(
                                            containerColor = getColorByPriority(priority)
                                        )
                                    } else {
                                        ButtonDefaults.buttonColors()
                                    }
                                ) {
                                    Text(priority.displayName)
                                }
                            }
                        }
                        Text("Select Date and Time")
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(onClick = {
                                    showDatePicker = true
                                }) {
                                    Text("Select Date")
                                }
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(selectedDate.toString())
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(onClick = {
                                    showTimePicker = true
                                }) {
                                    Text("Select Time")
                                }
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(selectedTime.toString().formatTime())
                                }
                            }
                        }
                        Button(onClick = {
                            viewModel.updateTask(
                                TaskItem.Task(
                                    id = task.id,
                                    taskTitle = taskText.text,
                                    timeSpent = task.timeSpent,
                                    priority = selectedPriority,
                                    timestamp = task.timestamp,
                                    date = selectedDate.toString(),
                                    time = selectedTime.toString(),
                                    isCompleted = task.isCompleted
                                ).mapToEntity()
                            )
                            navController.popBackStack()
                        }) {
                            Text("Save Changes")
                        }
                    }
                }
            )
        }
    }
}