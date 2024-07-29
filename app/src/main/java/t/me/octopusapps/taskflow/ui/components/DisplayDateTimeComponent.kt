package t.me.octopusapps.taskflow.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import t.me.octopusapps.taskflow.data.local.models.Task

@Composable
fun DisplayDateTime(task: Task) {
    if (task.date.isNotEmpty()) {
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "${task.date} ${task.time}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}