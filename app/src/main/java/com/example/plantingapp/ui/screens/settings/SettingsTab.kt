package com.example.plantingapp.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object SettingsTab: Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Settings"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(SettingsScreen())
    }
}