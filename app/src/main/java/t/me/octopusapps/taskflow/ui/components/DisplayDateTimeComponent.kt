package t.me.octopusapps.taskflow.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import t.me.octopusapps.taskflow.data.local.models.Task

@Composable
fun DisplayDateTime(task: Task) {
    Text(
        text = "${task.date} ${task.time}",
        style = MaterialTheme.typography.bodyMedium
    )
}