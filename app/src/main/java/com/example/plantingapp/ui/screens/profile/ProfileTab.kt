package com.example.plantingapp.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.plantingapp.ui.screens.empty.EmptyScreen
import com.example.plantingapp.ui.screens.explore.ExploreScreen

object ProfileTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Profile"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        ProfileScreen()
    }
}