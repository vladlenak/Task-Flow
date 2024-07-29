package t.me.octopusapps.taskflow.data.local.datastore

import t.me.octopusapps.taskflow.domain.constants.DataStoreConstants
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val booleanSource: BooleanDataStoreSource,
    private val stringSource: StringDataStoreSource
) {

    suspend fun getIsCompletedTasksVisible() =
        booleanSource.getValue(DataStoreConstants.KEY_IS_COMPLETED_TASKS_VISIBLE)

    suspend fun saveIsCompletedTasksVisible(isCompletedTasksVisible: Boolean) =
        booleanSource.saveValue(
            DataStoreConstants.KEY_IS_COMPLETED_TASKS_VISIBLE,
            isCompletedTasksVisible
        )

    suspend fun getGoal() = stringSource.getValue(DataStoreConstants.KEY_GOAL)

    suspend fun saveGoal(mainGoal: String) =
        stringSource.saveValue(DataStoreConstants.KEY_GOAL, mainGoal)
}