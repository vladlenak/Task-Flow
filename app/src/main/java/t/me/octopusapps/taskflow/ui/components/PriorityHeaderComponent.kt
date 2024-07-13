package t.me.octopusapps.taskflow.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import t.me.octopusapps.taskflow.data.local.models.FullPriority

@Composable
fun PriorityHeader(priority: FullPriority) {
    Text(
        text = priority.displayName,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}