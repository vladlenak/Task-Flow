package t.me.octopusapps.taskflow.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.data.remote.CrashlyticsRepository
import t.me.octopusapps.taskflow.utilities.RelativeDateHelper

@Composable
fun DisplayDateTime(task: Task, crashlyticsRepository: CrashlyticsRepository) {
    if (task.date.isNotEmpty()) {
        var relativeDateString = RelativeDateHelper.getRelativeDateString(task.date, crashlyticsRepository)
        if (task.time.isNotEmpty()) relativeDateString += " at ${task.time}"
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = relativeDateString,
            style = MaterialTheme.typography.bodySmall
        )
    }
}