package com.example.plantingapp.ui.screens.saved

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.plantingapp.R
import org.koin.androidx.compose.getViewModel

object SavedTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(id = R.string.saved)

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