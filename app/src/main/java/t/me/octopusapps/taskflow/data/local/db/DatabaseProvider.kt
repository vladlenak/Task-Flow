package t.me.octopusapps.taskflow.data.local.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var instance: TaskDatabase? = null

    fun getDatabase(context: Context): TaskDatabase {
        if (instance == null) {
            synchronized(TaskDatabase::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java, "task_database"
                    ).build()
                }
            }
        }
        return instance!!
    }
}