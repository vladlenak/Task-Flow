package t.me.octopusapps.taskflow.ui.screens.taskeditor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.db.TaskDatabase
import t.me.octopusapps.taskflow.data.local.models.Task
import javax.inject.Inject

@HiltViewModel
class TaskEditorViewModel @Inject constructor(
    private val taskDatabase: TaskDatabase
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskEditorState> =
        MutableStateFlow(TaskEditorState(TaskEditorItem.Skeleton))
    val usState = _uiState.asStateFlow()

    fun loadTask(taskId: String) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = _uiState.value.copy(
            task = TaskEditorItem.TaskEditor(taskDatabase.taskDao().getTaskById(taskId))
        )
    }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDatabase.taskDao().update(task)
    }

}