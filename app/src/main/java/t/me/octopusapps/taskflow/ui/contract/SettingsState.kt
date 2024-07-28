package t.me.octopusapps.taskflow.ui.contract

import t.me.octopusapps.taskflow.ui.models.SettingsItem

data class SettingsState(
    val settings: SettingsItem = SettingsItem.Skeleton
)