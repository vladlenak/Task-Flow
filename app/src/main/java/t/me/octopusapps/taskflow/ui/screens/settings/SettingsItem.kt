package t.me.octopusapps.taskflow.ui.screens.settings

sealed class SettingsItem {
    class Settings(
        val isCompletedTasksVisible: Boolean?
    ) : SettingsItem()
    data object Skeleton: SettingsItem()
}