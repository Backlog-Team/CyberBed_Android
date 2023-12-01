package com.example.plantingapp.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.plantingapp.R
import com.example.plantingapp.ui.components.SettingsItem
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.screens.settings.account.AccountScreen
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsView() {
    val navigator = LocalNavigator.current
    TabView {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.settings),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            if (navigator != null) {
                SettingsItem(navigator = navigator, screen = BluetoothScreen(), text = "Bluetooth")
                SettingsItem(
                    navigator = navigator,
                    screen = AccountScreen(getViewModel()),
                    text = stringResource(R.string.profile)
                )
            }
        }
    }
}