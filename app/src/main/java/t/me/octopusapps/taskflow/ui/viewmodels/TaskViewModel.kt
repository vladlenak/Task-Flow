package t.me.octopusapps.taskflow.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.db.DatabaseProvider
import t.me.octopusapps.taskflow.data.local.models.Priority
import t.me.octopusapps.taskflow.data.local.models.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = DatabaseProvider.getDatabase(application).taskDao()
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    init {
        viewModelScope.launch {
            _tasks.value = taskDao.getAllTasks()
        }
    }

    fun addTask(taskText: String, priority: Priority) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val newTask = Task(
            id = _tasks.value.size + 1,
            taskText = taskText,
            timestamp = timestamp,
            timeSpent = 0L,
            priority = priority
        )
        viewModelScope.launch {
            taskDao.insert(newTask)
            _tasks.value += newTask
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.update(task)
            _tasks.value = _tasks.value.map { if (it.id == task.id) task else it }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
            _tasks.value = _tasks.value.filter { it.id != task.id }
        }
    }

    fun getTaskById(id: Int): Task? {
        return _tasks.value.find { it.id == id }
    }

}