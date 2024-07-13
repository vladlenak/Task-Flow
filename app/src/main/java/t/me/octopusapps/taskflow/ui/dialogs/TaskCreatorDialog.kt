package t.me.octopusapps.taskflow.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun TaskCreatorDialog(onDismiss: () -> Unit, onAddTask: (String) -> Unit) {
    var taskText by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onAddTask(taskText.text)
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
            }
        }
    )
}