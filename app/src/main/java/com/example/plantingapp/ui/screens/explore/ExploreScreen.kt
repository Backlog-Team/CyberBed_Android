package com.example.plantingapp.ui.screens.explore

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class ExploreScreen(
    private val viewModel: ExploreViewModel
): Screen {
    @Composable
    override fun Content() {
        ExploreView(viewModel)
    }
}