package t.me.octopusapps.taskflow.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import t.me.octopusapps.taskflow.data.local.db.TaskDatabase

@Module
@InstallIn(ViewModelComponent::class)
object RoomModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

}