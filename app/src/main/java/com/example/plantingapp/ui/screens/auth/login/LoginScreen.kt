package com.example.plantingapp.ui.screens.auth.login

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.ui.screens.auth.AuthViewModel

class LoginScreen(
    private val viewModel: AuthViewModel
): Screen {
    @Composable
    override fun Content() {
        LoginView(viewModel)
    }
}