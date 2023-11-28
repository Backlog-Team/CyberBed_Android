package com.example.plantingapp.ui.screens.settings.bluetooth

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.plantingapp.management.PermissionsManager
import com.example.plantingapp.ui.components.containers.TabView
import org.koin.androidx.scope.activityScope
import org.koin.compose.koinInject


@Composable
fun BluetoothView(viewModel: BluetoothViewModel) {
    val isConnected = viewModel.isConnected
    val pairedDevices = viewModel.pairedDevices
    var deviceName by remember { mutableStateOf("") }
    val activity = LocalContext.current as ComponentActivity
    val scope by activity.activityScope()
    val pm: PermissionsManager = koinInject(scope = scope)
    TabView {
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
            LazyColumn {
                if (pairedDevices != null) {
                    items(pairedDevices.size) { index ->
                        DropdownMenuItem(onClick = {
                            Log.d("kilo", "Device clicked")
                        }) {
                            if (pm.checkBtPermission())
                                Text(pairedDevices.toList()[index].name ?: "-")
                        }
                    }
                }
            }
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
}