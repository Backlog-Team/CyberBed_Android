package com.example.plantingapp.ui.screens.settings.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.screens.auth.AuthViewModel
import com.example.plantingapp.ui.screens.auth.SplashScreen

@Composable
fun AccountView(
    viewModel: AuthViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    Box(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            viewModel.logout()
            navigator.parent?.parent?.replace(SplashScreen(viewModel))
        }) {
            Text("Выйти")
        }
    }
}