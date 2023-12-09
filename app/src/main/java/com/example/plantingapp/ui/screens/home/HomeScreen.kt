package com.example.plantingapp.ui.screens.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.ui.screens.saved.SavedViewModel

class HomeScreen(
    private val viewModel: SavedViewModel
): Screen {
    @Composable
    override fun Content() {
        HomeView(viewModel)
    }
}