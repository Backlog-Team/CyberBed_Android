package com.example.plantingapp.ui.screens.home.folders

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class FoldersScreen(
    private val viewModel: FoldersViewModel
): Screen {
    @Composable
    override fun Content() {
        FoldersView(viewModel)
    }
}