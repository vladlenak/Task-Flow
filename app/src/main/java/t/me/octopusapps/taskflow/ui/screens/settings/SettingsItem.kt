package t.me.octopusapps.taskflow.ui.screens.settings

sealed class SettingsItem {
    class Settings(
        val isCompletedTasksVisible: Boolean?,
        val isPlannedTasksVisible: Boolean?,
        val goal: String
    ) : SettingsItem()
    data object Skeleton: SettingsItem()
}