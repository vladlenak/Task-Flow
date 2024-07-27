package t.me.octopusapps.taskflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.db.TaskDatabase
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.ui.contract.TaskEditorState
import t.me.octopusapps.taskflow.ui.models.TaskItem
import t.me.octopusapps.taskflow.ui.models.mapToView
import javax.inject.Inject

@HiltViewModel
class TaskEditorViewModel @Inject constructor(
    private val taskDatabase: TaskDatabase
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskEditorState> =
        MutableStateFlow(TaskEditorState(TaskItem.Skeleton))
    val usState = _uiState.asStateFlow()

    fun loadTask(taskId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = _uiState.value.copy(
            task = taskDatabase.taskDao().getTaskById(taskId).mapToView()
        )
    }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDatabase.taskDao().update(task)
    }

}