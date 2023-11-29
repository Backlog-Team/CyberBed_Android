package com.example.plantingapp.ui.screens.explore.custom.create

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class CustomCreateScreen(
    private val viewModel: CustomCreateViewModel,
    private val isCreate: Boolean = true,
    private val isEdit: Boolean = false,
    private val id: Int? = null
): Screen {
    @Composable
    override fun Content() {
        CustomCreateView(viewModel, isCreate, isEdit, id)
    }
}