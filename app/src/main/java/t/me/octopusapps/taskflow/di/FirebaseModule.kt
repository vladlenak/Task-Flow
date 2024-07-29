package t.me.octopusapps.taskflow.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import t.me.octopusapps.taskflow.data.remote.CrashlyticsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideCrashlyticsRepository() = CrashlyticsRepository()
}