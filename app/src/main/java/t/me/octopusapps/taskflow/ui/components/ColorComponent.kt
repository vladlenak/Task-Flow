package t.me.octopusapps.taskflow.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import t.me.octopusapps.taskflow.domain.models.Priority

@Composable
fun getColorByPriority(priority: Priority): Color {
    return when (priority) {
        Priority.A -> Color.Red
        Priority.B -> Color.Green
        Priority.C -> Color.Yellow
        Priority.D -> Color.Gray
    }
}