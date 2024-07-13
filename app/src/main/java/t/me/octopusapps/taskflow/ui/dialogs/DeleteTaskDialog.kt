package t.me.octopusapps.taskflow.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DeleteTaskDialog(
    onDismiss: () -> Unit,
    onConfirmButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {

        },
        title = {
            Text("Confirm Deletion")
        },
        text = {
            Text("Are you sure you want to delete this task?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmButton()
                    onDismiss()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}