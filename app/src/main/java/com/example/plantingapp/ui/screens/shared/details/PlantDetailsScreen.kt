package com.example.plantingapp.ui.screens.shared.details

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import coil.request.ImageRequest
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.explore.ExploreViewModel

class PlantDetailsScreen(
    private val plant: Plant,
    private val viewModel: ExploreViewModel,
    private val imageRequest: ImageRequest
): Screen {
    @Composable
    override fun Content() {
        PlantDetailsView(plant, viewModel, imageRequest)
    }
}