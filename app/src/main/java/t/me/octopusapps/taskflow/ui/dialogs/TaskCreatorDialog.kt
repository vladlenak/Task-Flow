package t.me.octopusapps.taskflow.ui.dialogs

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import t.me.octopusapps.taskflow.data.local.models.Priority
import t.me.octopusapps.taskflow.ui.components.getColorByPriority
import t.me.octopusapps.taskflow.ui.ext.formatTime
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TaskCreatorDialog(
    onDismiss: () -> Unit,
    onAddTask: (String, Priority, LocalDate, LocalTime) -> Unit
) {
    var taskText by remember { mutableStateOf(TextFieldValue()) }
    var selectedPriority by remember { mutableStateOf(Priority.A) }
    val priorities = Priority.entries.toTypedArray()
    val context = LocalContext.current

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
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

    if (showTimePicker) {
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

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onAddTask(taskText.text, selectedPriority, selectedDate, selectedTime)
                onDismiss()
            }) {
                Text("Add Task")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        title = { Text("Create New Task") },
        text = {
            Column {
                TextField(
                    value = taskText,
                    onValueChange = { taskText = it },
                    label = { Text("Task Title") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Select Priority")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    priorities.forEach { priority ->
                        Button(
                            onClick = { selectedPriority = priority },
                            colors = if (selectedPriority == priority) {
                                ButtonDefaults.buttonColors(
                                    containerColor = getColorByPriority(
                                        priority = priority
                                    )
                                )
                            } else {
                                ButtonDefaults.buttonColors()
                            }
                        ) {
                            Text(priority.displayName)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Select Date and Time")
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { showDatePicker = true }) {
                            Text("Select Date")
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = { showTimePicker = true }) {
                            Text("Select Time")
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(selectedTime.toString().formatTime())
                        }
                    }
                }
            }
        }
    )
}