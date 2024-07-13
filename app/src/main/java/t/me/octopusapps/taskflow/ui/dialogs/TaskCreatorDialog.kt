package t.me.octopusapps.taskflow.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import t.me.octopusapps.taskflow.data.local.models.Priority
import t.me.octopusapps.taskflow.ui.components.getColorByPriority

@Composable
fun TaskCreatorDialog(onDismiss: () -> Unit, onAddTask: (String, Priority) -> Unit) {
    var taskText by remember { mutableStateOf(TextFieldValue()) }
    var selectedPriority by remember { mutableStateOf(Priority.A) }
    val priorities = Priority.entries.toTypedArray()

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onAddTask(taskText.text, selectedPriority)
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
                                ButtonDefaults.buttonColors(containerColor = getColorByPriority(priority = priority))
                            } else {
                                ButtonDefaults.buttonColors()
                            }
                        ) {
                            Text(priority.displayName)
                        }
                    }
                }
            }
        }
    )
}