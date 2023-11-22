package com.example.plantingapp.ui.screens.home.folders.details

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel

class FolderDetailsScreen(
    folder: Folder,
    private val viewModel: FoldersViewModel
): Screen {
    @Composable
    override fun Content() {
        FolderDetailsView()
    }
}