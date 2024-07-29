package t.me.octopusapps.taskflow.ui.screens.stopwatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.db.TaskDatabase
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.data.remote.CrashlyticsRepository
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val taskDatabase: TaskDatabase,
    private val crashlyticsRepository: CrashlyticsRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<StopwatchState> =
        MutableStateFlow(StopwatchState(StopwatchItem.Skeleton))
    val usState = _uiState.asStateFlow()

    fun loadTask(taskId: String) = viewModelScope.launch {
        try {
            taskDatabase.taskDao().getTaskById(taskId)?.let { task ->
                _uiState.value = _uiState.value.copy(
                    stopwatchItem = StopwatchItem.Stopwatch(task = task)
                )
            }
        } catch (e: Exception) {
            crashlyticsRepository.sendCrashlytics(e)
        }
    }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        try {
            taskDatabase.taskDao().update(task)
        } catch (e: Exception) {
            crashlyticsRepository.sendCrashlytics(e)
        }
    }

    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        try {
            taskDatabase.taskDao().delete(task)
        } catch (e: Exception) {
            crashlyticsRepository.sendCrashlytics(e)
        }
    }

}