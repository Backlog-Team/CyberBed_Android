package com.example.plantingapp.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.plantingapp.ui.components.SettingsItem
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothScreen

@Composable
fun SettingsView() {
    val navigator = LocalNavigator.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 30.dp,
                horizontal = 20.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = "username",
                fontSize = 16.sp
            )
        }
        if (navigator != null) {
            SettingsItem(navigator = navigator, screen = BluetoothScreen(), text = "Bluetooth")
            //SettingsItem(navigator = navigator, screen = BluetoothScreen(), text = "Profile")
        }
    }
}