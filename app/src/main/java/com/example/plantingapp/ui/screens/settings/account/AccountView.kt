package com.example.plantingapp.ui.screens.settings.account

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.R
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.screens.auth.AuthViewModel
import com.example.plantingapp.ui.screens.auth.intro.SplashScreen

@Composable
fun AccountView(
    viewModel: AuthViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    TabView {
        Button(onClick = {
            viewModel.logout()
            navigator.parent?.parent?.replace(SplashScreen(viewModel))
        }) {
            Text(stringResource(R.string.log_out))
        }
    }
}