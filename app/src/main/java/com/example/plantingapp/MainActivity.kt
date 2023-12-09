package com.example.plantingapp

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import com.example.plantingapp.management.PermissionsManager
import com.example.plantingapp.ui.screens.auth.intro.SplashScreen
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothViewModel
import com.example.plantingapp.ui.screens.settings.bluetooth.REQUEST_ENABLE_BT
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.scope.Scope


class MainActivity : ComponentActivity(), AndroidScopeComponent {
    override val scope: Scope by activityScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pm: PermissionsManager = scope.get()//PermissionsManager(this)
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
            //PlantingAppTheme(darkTheme = false) {
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
        //}
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}
