package com.example.plantingapp.ui.screens.saved

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class SavedScreen(
    private val viewModel: SavedViewModel
): Screen {
    @Composable
    override fun Content() {
        SavedView(viewModel)
    }
}