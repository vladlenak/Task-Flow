package t.me.octopusapps.taskflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.utilities.TimeFormatHelper

@Composable
fun TaskItem(
    task: Task,
    onClickItem: (Int) -> Unit,
    onCheckedChange: (Task, Boolean) -> Unit,
    onClickPlay: (Task) -> Unit
) {
    var isChecked by remember { mutableStateOf(task.isCompleted) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(task.id) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = getColorByPriority(task.priority),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        isChecked = checked
                        onCheckedChange(task, checked)
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = task.taskTitle,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                DisplayDateTime(task = task)
            }

            Row {
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = TimeFormatHelper.getTimeStr(task.timeSpent),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                IconButton(
                    onClick = { onClickPlay(task) },
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Task Action",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }


        }

    }
}