package com.example.plantingapp.ui.screens.settings.bluetooth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BluetoothView(viewModel: BluetoothViewModel){
    val isConnected = viewModel.isConnected
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isConnected.value) {
            Text("Bluetooth устройство не подключено")
            Text("Подключите устройство HC-05 в настройках приложения")
            Button(onClick = { viewModel.connectToHC() }) {
                Text("Повторить подключение")
            }
        } else {
            Text("Bluetooth устройство подключено")
            Button(onClick = { viewModel.sendMessageToHC("Channel_1") }) {
                Text("Channel_1")
            }
            Button(onClick = { viewModel.sendMessageToHC("Channel_2") }) {
                Text("Channel_2")
            }
        }
    }
}