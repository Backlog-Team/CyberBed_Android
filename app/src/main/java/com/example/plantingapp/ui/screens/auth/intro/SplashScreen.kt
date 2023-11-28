package com.example.plantingapp.ui.screens.auth.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.screens.BaseScreen
import com.example.plantingapp.ui.screens.auth.AuthStates
import com.example.plantingapp.ui.screens.auth.AuthViewModel
import com.example.plantingapp.ui.screens.auth.login.LoginScreen

class SplashScreen(
    private val viewModel: AuthViewModel
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        viewModel.auth()
        when (viewModel.authState.collectAsState().value) {
            AuthStates.LoggedIn -> navigator.replace(BaseScreen())
            AuthStates.LoggedOut -> navigator.replace(LoginScreen(viewModel))
            AuthStates.Logging -> Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}