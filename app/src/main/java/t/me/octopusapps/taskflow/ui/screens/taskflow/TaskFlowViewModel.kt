package t.me.octopusapps.taskflow.ui.screens.taskflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.datastore.DataStoreRepository
import t.me.octopusapps.taskflow.data.local.db.TaskDatabase
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.domain.constants.CommonConstants
import t.me.octopusapps.taskflow.domain.ext.formatTime
import t.me.octopusapps.taskflow.domain.models.Priority
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TaskFlowViewModel @Inject constructor(
    private val taskDatabase: TaskDatabase,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<TaskFlowState> =
        MutableStateFlow(TaskFlowState(TaskFlowItem.Skeleton))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                tasksItem = TaskFlowItem.Tasks(
                    tasks = taskDatabase.taskDao().getAllTasks(),
                    isCompletedTasksVisible = dataStoreRepository.getIsCompletedTasksVisible()
                        ?: true
                )
            )
        }
    }

    fun addTask(
        taskText: String,
        priority: Priority,
        selectedDate: LocalDate,
        selectedTime: LocalTime
    ) {
        val timestamp =
            SimpleDateFormat(CommonConstants.TIMESTAMP_PATTERN, Locale.getDefault()).format(Date())
        val newTask = Task(
            taskTitle = taskText,
            timestamp = timestamp,
            timeSpent = 0L,
            priority = priority,
            date = selectedDate.toString(),
            time = selectedTime.toString().formatTime(),
            isCompleted = false
        )
        viewModelScope.launch {
            taskDatabase.taskDao().insert(newTask)
            refreshTasks()
        }
    }

    fun refreshTasks() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            tasksItem = TaskFlowItem.Tasks(
                tasks = taskDatabase.taskDao().getAllTasks(),
                isCompletedTasksVisible = dataStoreRepository.getIsCompletedTasksVisible() ?: true
            )
        )
    }

    fun onCheckedChange(task: Task, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        taskDatabase.taskDao().update(task.copy(isCompleted = isChecked))
        refreshTasks()
    }

}