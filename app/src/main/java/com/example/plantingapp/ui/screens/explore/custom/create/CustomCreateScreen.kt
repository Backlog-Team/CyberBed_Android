package com.example.plantingapp.ui.screens.explore.custom.create

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class CustomCreateScreen(
    private val viewModel: CustomCreateViewModel
): Screen {
    @Composable
    override fun Content() {
        CustomCreateView(viewModel)
    }
}