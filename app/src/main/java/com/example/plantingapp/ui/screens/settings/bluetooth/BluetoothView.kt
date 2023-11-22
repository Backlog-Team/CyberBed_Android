package com.example.plantingapp.ui.screens.settings.bluetooth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothView(viewModel: BluetoothViewModel){
    val isConnected = viewModel.isConnected
    var deviceName by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = "Bluetooth",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Введите название устройства",
            modifier = Modifier.padding(top = 50.dp)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = deviceName,
            onValueChange = { deviceName = it }
        )
        when (!isConnected.value) {
            true -> {
                Text("Bluetooth устройство не подключено")
                Text("Подключите устройство в настройках устройства")
            }
            false -> {
                Text("Bluetooth устройство подключено")
                Button(onClick = { viewModel.sendMessageToHC("Channel_1") }) {
                    Text("Channel_1")
                }
                Button(onClick = { viewModel.sendMessageToHC("Channel_2") }) {
                    Text("Channel_2")
                }
            }
        }
        Button(onClick = { viewModel.connectToHC(deviceName) }) {
            Text("Повторить подключение")
        }
    }
}