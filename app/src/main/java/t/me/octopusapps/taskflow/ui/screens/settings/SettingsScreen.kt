package t.me.octopusapps.taskflow.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val viewState = viewModel.uiState.collectAsState()
    viewModel.restoreUiState()

    when (val settingsItem = viewState.value.settingsItem) {
        SettingsItem.Skeleton -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Text(
                    text = "Settings error",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is SettingsItem.Settings -> {
            var taskText by remember { mutableStateOf(TextFieldValue(settingsItem.goal)) }
            var hidePlannedTasks by remember {
                mutableStateOf(settingsItem.isPlannedTasksVisible ?: true)
            }
            var hideCompletedTasks by remember {
                mutableStateOf(settingsItem.isCompletedTasksVisible ?: true)
            }

            BackHandler {
                viewModel.saveUiState(taskText.text, hidePlannedTasks, hideCompletedTasks)
                navController.popBackStack()
            }

            Scaffold(
                topBar = {
                    androidx.compose.material3.TopAppBar(
                        title = { Text("Settings") },
                        navigationIcon = {
                            IconButton(onClick = {
                                viewModel.saveUiState(
                                    taskText.text,
                                    hidePlannedTasks,
                                    hideCompletedTasks
                                )
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            value = taskText,
                            onValueChange = {
                                taskText = it
                            },
                            label = { Text("Goal") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.Sentences
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Hide planned tasks", modifier = Modifier.weight(1f))
                            Checkbox(
                                checked = hidePlannedTasks,
                                onCheckedChange = { hidePlannedTasks = it }
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Hide completed tasks", modifier = Modifier.weight(1f))
                            Checkbox(
                                checked = hideCompletedTasks,
                                onCheckedChange = { hideCompletedTasks = it }
                            )
                        }
                    }
                }
            )
        }
    }
}