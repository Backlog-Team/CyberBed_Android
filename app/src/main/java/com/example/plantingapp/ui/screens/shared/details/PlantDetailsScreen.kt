package com.example.plantingapp.ui.screens.shared.details

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.domain.models.Plant

class PlantDetailsScreen(
    private val plant: Plant
): Screen {
    @Composable
    override fun Content() {
        PlantDetailsView(plant)
    }
}