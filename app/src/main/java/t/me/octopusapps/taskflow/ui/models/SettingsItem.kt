package t.me.octopusapps.taskflow.ui.models

sealed class SettingsItem {
    class Settings(
        val isCompletedTasksVisible: Boolean?
    ) : SettingsItem()
    data object Skeleton: SettingsItem()
}