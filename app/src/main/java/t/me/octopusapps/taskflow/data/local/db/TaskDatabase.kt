package t.me.octopusapps.taskflow.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import t.me.octopusapps.taskflow.data.local.models.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(PriorityConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "task_database"
    }

    abstract fun taskDao(): TaskDao

}