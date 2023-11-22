package com.example.plantingapp.ui.screens.auth

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
import com.example.plantingapp.ui.LoadingStates
import com.example.plantingapp.ui.screens.BaseScreen

class SplashScreen(
    private val viewModel: AuthViewModel
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow


        viewModel.auth()
        when (viewModel.authState.collectAsState().value) {
             LoadingStates.Success -> navigator.replace(BaseScreen())
             LoadingStates.Error -> navigator.replace(LoginScreen(viewModel))
            LoadingStates.Loading -> Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {navigator.replace(LoginScreen(viewModel))}
        }
    }
}