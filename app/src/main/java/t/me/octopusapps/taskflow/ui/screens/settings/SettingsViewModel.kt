package t.me.octopusapps.taskflow.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import t.me.octopusapps.taskflow.data.local.datastore.DataStoreRepository
import t.me.octopusapps.taskflow.data.remote.CrashlyticsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val crashlyticsRepository: CrashlyticsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsState> =
        MutableStateFlow(SettingsState(SettingsItem.Skeleton))
    val uiState = _uiState.asStateFlow()

    fun getIsCompletedTasksVisible() = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(
                settings = SettingsItem.Settings(
                    isCompletedTasksVisible = dataStoreRepository.getIsCompletedTasksVisible()
                )
            )
        } catch (e: Exception) {
            crashlyticsRepository.sendCrashlytics(e)
        }
    }

    fun saveIsCompletedTasksVisible(isCompletedTasksVisible: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataStoreRepository.saveIsCompletedTasksVisible(
                    isCompletedTasksVisible = isCompletedTasksVisible
                )
            } catch (e: Exception) {
                crashlyticsRepository.sendCrashlytics(e)
            }
        }

}