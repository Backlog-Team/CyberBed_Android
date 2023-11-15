package com.example.plantingapp.ui.screens.explore.search

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.ui.screens.explore.ExploreViewModel

class SearchScreen (
    private val viewModel: ExploreViewModel
): Screen {
    @Composable
    override fun Content() {
        SearchView(viewModel)
    }
}