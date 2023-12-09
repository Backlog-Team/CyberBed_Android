package com.example.plantingapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantingapp.R
import com.example.plantingapp.ui.screens.explore.ExploreTab
import com.example.plantingapp.ui.screens.folders.FoldersTab
import com.example.plantingapp.ui.screens.home.HomeTab
import com.example.plantingapp.ui.screens.saved.SavedTab
import com.example.plantingapp.ui.screens.scan.ScanTab
import com.example.plantingapp.ui.screens.settings.SettingsTab
import com.example.plantingapp.ui.theme.GreenPrimary

@Composable
@Preview(showBackground = false)
fun TabNavigationBar() {
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
            ScanTab,
            painterResource(R.drawable.ic_scan),
            painterResource(R.drawable.ic_scan)
        )
        TabNavigationItem(
            FoldersTab,
            painterResource(R.drawable.ic_folders_selected),
            painterResource(R.drawable.ic_folders_unselected)
        )
        TabNavigationItem(
            SettingsTab,
            painterResource(R.drawable.ic_settings_selected),
            painterResource(R.drawable.ic_settings_unselected)
        )
    }
}