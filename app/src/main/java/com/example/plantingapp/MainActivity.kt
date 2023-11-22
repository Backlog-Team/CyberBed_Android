package com.example.plantingapp

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import cafe.adriel.voyager.navigator.Navigator
import com.example.plantingapp.management.PermissionsManager
import com.example.plantingapp.ui.screens.auth.SplashScreen
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothViewModel
import com.example.plantingapp.ui.screens.settings.bluetooth.REQUEST_ENABLE_BT
import com.example.plantingapp.ui.theme.PlantingAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("kilo", this.resources.configuration.screenWidthDp.toString())
        super.onCreate(savedInstanceState)
        val pm = PermissionsManager(this)
        pm.requestBtPermission()
        pm.requestCameraPermission()
        val btViewModel = getViewModel<BluetoothViewModel>()
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(
                this, enableBtIntent,
                REQUEST_ENABLE_BT, null
            )
        }
        btViewModel.bluetoothAdapter = bluetoothAdapter
        btViewModel.pm = pm
        setContent {
            PlantingAppTheme(darkTheme = false) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                    systemUiController.setNavigationBarColor(color = Color.Transparent)
                }
                Navigator(SplashScreen(getViewModel()))
            }
        }
    }
}
