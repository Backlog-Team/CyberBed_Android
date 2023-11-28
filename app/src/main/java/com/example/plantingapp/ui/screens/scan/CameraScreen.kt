package com.example.plantingapp.ui.screens.scan

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class CameraScreen(
    private val viewModel: ScanViewModel
): Screen {
    @Composable
    override fun Content() {
        CameraView(viewModel)
    }
}