package com.example.plantingapp.ui.screens.settings.bluetooth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.plantingapp.management.PermissionsManager
import com.example.plantingapp.ui.components.containers.TabView
import kotlinx.coroutines.launch
import org.koin.androidx.scope.activityScope
import org.koin.compose.koinInject


@Composable
fun BluetoothView(viewModel: BluetoothViewModel) {
    val isConnected = viewModel.isConnected
    val context = LocalContext.current
    val activity = LocalContext.current as ComponentActivity
    val scope by activity.activityScope()
    val pm: PermissionsManager = koinInject(scope = scope)
    viewModel.getPairedDevices()
    var pairedDevices = viewModel.pairedDevices
    var deviceName by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    TabView {
        /*LaunchedEffect(Unit) {
            coroutineScope.launch {
                viewModel.message.collect {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }*/
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
            Text("Выберите устройство из списка сопряженных: ")
            Text("(устройство должно быть подключено!)")

            LazyColumn {
                items(pairedDevices.size) { index ->
                    if (pm.checkBtPermission()) {
                        val device = pairedDevices.toList()[index]
                        ListItem(headlineContent = {
                            Row(horizontalArrangement = Arrangement.SpaceAround) {
                                Text(device.name)
                            }
                        },
                            modifier = Modifier.clickable {
                                viewModel.connectToHC(device.name)
                                deviceName = device.name
                            })
                    }
                }
            }
            when (!isConnected.value) {
            true -> {
                Text("Bluetooth устройство не подключено")
                Text("Подключите устройство в настройках устройства")
            }

            false -> {
                Text("Bluetooth устройство $deviceName подключено")
                Button(onClick = { viewModel.sendMessageToHC("Channel_1") }) {
                    Text("Channel_1")
                }
                Button(onClick = { viewModel.sendMessageToHC("Channel_2") }) {
                    Text("Channel_2")
                }
            }
        }
            Button(onClick = {
                viewModel.getPairedDevices()
                pairedDevices = viewModel.pairedDevices
            }) {
                Text("Повторить подключение")
            }
        }
    }
}