package com.example.plantingapp.ui.screens.explore.custom.plants

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class CustomScreen(
    private val viewModel: CustomViewModel
): Screen {
    @Composable
    override fun Content() {
        CustomView(viewModel)
    }
}