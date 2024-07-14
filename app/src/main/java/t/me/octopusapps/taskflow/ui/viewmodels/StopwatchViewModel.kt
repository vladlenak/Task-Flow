package t.me.octopusapps.taskflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.db.TaskDatabase
import t.me.octopusapps.taskflow.data.local.models.Task
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val taskDatabase: TaskDatabase
) : ViewModel() {

    private var tasks = listOf<Task>()

    init {
        viewModelScope.launch {
            tasks = taskDatabase.taskDao().getAllTasks()
        }
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskDatabase.taskDao().update(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDatabase.taskDao().delete(task)
    }

    fun getTaskById(id: Int): Task? {
        return tasks.find { it.id == id }
    }
}