package t.me.octopusapps.taskflow.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.db.TaskDatabase
import t.me.octopusapps.taskflow.data.local.models.Priority
import t.me.octopusapps.taskflow.data.local.models.Task
import t.me.octopusapps.taskflow.ui.ext.formatTime
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDatabase: TaskDatabase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> get() = _tasks

    init {
        viewModelScope.launch {
            _tasks.value = taskDatabase.taskDao().getAllTasks()
        }
    }

    fun addTask(taskText: String, priority: Priority, selectedDate: LocalDate, selectedTime: LocalTime) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        val newTask = Task(
            id = _tasks.value.size + 1,
            taskTitle = taskText,
            timestamp = timestamp,
            timeSpent = 0L,
            priority = priority,
            date = selectedDate.toString(),
            time = selectedTime.toString().formatTime()
        )
        viewModelScope.launch {
            taskDatabase.taskDao().insert(newTask)
            _tasks.value += newTask
        }
    }

    fun refreshTasks() = viewModelScope.launch {
        _tasks.value = taskDatabase.taskDao().getAllTasks()
    }

    fun getTaskById(id: Int): Task? {
        return tasks.value.find { it.id == id }
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskDatabase.taskDao().update(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskDatabase.taskDao().delete(task)
    }

}