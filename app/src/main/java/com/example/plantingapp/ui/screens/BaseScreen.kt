package com.example.plantingapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.plantingapp.R
import com.example.plantingapp.ui.components.TabNavigationItem
import com.example.plantingapp.ui.screens.camera.CameraTab
import com.example.plantingapp.ui.screens.explore.ExploreTab
import com.example.plantingapp.ui.screens.home.HomeTab
import com.example.plantingapp.ui.screens.saved.SavedTab
import com.example.plantingapp.ui.screens.settings.SettingsTab


class BaseScreen: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        TabNavigator(HomeTab) {
            Scaffold(
                content = {innerPadding ->
                    Box(modifier = Modifier.padding(
                        PaddingValues(bottom = innerPadding.calculateBottomPadding())
                    )){
                        CurrentTab()
                    }
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 10.dp
                    ) {
                        TabNavigationItem(
                            HomeTab,
                            painterResource(R.drawable.ic_home_selected),
                            painterResource(R.drawable.ic_home_unselected)
                        )
                        TabNavigationItem(
                            ExploreTab,
                            painterResource(R.drawable.ic_explore_selected),
                            painterResource(R.drawable.ic_explore_unselected)
                        )
                        TabNavigationItem(
                            CameraTab,
                            painterResource(R.drawable.ic_scan),
                            painterResource(R.drawable.ic_scan)
                        )
                        TabNavigationItem(
                            SavedTab,
                            painterResource(R.drawable.ic_saved_selected),
                            painterResource(R.drawable.ic_saved_unselected)
                        )
                        TabNavigationItem(
                            SettingsTab,
                            painterResource(R.drawable.ic_settings_selected),
                            painterResource(R.drawable.ic_settings_unselected)
                        )
                    }
                }
            )
        }
    }
}