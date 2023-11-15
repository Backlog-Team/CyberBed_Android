package com.example.plantingapp.ui.screens.auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class LoginScreen(
    private val viewModel: AuthViewModel
): Screen {
    @Composable
    override fun Content() {
        LoginView(viewModel)
    }
}