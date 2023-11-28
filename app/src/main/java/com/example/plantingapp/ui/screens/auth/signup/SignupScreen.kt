package com.example.plantingapp.ui.screens.auth.signup

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.plantingapp.ui.screens.auth.AuthViewModel

class SignupScreen(
    private val viewModel: AuthViewModel
): Screen {
    @Composable
    override fun Content() {
        SignupView(viewModel)
    }
}