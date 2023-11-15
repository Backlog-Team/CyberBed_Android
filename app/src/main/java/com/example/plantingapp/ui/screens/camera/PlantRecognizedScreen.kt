package com.example.plantingapp.ui.screens.camera

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class PlantRecognizedScreen(private val viewModel: CameraViewModel): Screen {
    @Composable
    override fun Content() {
        PlantRecognizedView(viewModel)
    }
}