package com.example.plantingapp.ui.screens.shared.details

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant

class PlantDetailsScreen(
    private val plant: Plant,
    private val isSaved: Boolean = false,
    private val folder: Folder? = null
): Screen {
    @Composable
    override fun Content() {
        PlantDetailsView(plant, isSaved, folder)
    }
}