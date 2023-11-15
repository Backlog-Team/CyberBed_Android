package com.example.plantingapp.ui.screens.settings.bluetooth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import org.koin.androidx.compose.getViewModel

class BluetoothScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<BluetoothViewModel>()
        BluetoothView(viewModel)
    }
}