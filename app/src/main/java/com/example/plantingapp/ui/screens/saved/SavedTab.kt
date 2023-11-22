package com.example.plantingapp.ui.screens.saved

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.androidx.compose.getViewModel

object SavedTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Saved"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(SavedScreen(getViewModel()))
    }
}