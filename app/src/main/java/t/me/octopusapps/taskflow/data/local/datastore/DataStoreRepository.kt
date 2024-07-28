package t.me.octopusapps.taskflow.data.local.datastore

import t.me.octopusapps.taskflow.domain.constants.DataStoreConstants
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val booleanDataStoreSource: BooleanDataStoreSource
) {

    suspend fun getIsCompletedTasksVisible() = booleanDataStoreSource.getValue(DataStoreConstants.KEY_IS_COMPLETED_TASKS_VISIBLE)

    suspend fun saveIsCompletedTasksVisible(isCompletedTasksVisible: Boolean) =
        booleanDataStoreSource.saveValue(DataStoreConstants.KEY_IS_COMPLETED_TASKS_VISIBLE, isCompletedTasksVisible)
}