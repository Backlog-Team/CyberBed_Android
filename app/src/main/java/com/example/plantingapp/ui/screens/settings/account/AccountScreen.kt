package com.example.plantingapp.ui.screens.settings.account

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.ui.screens.auth.AuthViewModel

class AccountScreen(
    private val viewModel: AuthViewModel
): Screen {
    @Composable
    override fun Content() {
        AccountView(viewModel)
    }
}