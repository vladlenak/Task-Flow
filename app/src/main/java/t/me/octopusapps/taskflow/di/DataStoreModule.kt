package t.me.octopusapps.taskflow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import t.me.octopusapps.taskflow.data.local.datastore.BooleanDataStoreSource
import t.me.octopusapps.taskflow.data.local.datastore.DataStoreRepository
import t.me.octopusapps.taskflow.data.local.datastore.StringDataStoreSource
import t.me.octopusapps.taskflow.domain.constants.DataStoreConstants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreConstants.DATA_STORE_NAME
    )

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.userDataStore

    @Singleton
    @Provides
    fun provideBooleanDataStoreSource(dataStore: DataStore<Preferences>): BooleanDataStoreSource =
        BooleanDataStoreSource(dataStore)

    @Singleton
    @Provides
    fun provideStringDataStoreSource(dataStore: DataStore<Preferences>): StringDataStoreSource =
        StringDataStoreSource(dataStore)

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        booleanDataStoreSource: BooleanDataStoreSource,
        stringDataStoreSource: StringDataStoreSource
    ): DataStoreRepository =
        DataStoreRepository(booleanDataStoreSource, stringDataStoreSource)

}