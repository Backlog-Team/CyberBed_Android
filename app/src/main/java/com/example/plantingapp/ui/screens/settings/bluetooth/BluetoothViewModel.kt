package com.example.plantingapp.ui.screens.settings.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantingapp.management.PermissionsManager
import java.util.UUID

const val REQUEST_ENABLE_BT = 1

class BluetoothViewModel: ViewModel() {
    private val MY_UUID: UUID = UUID.fromString("c41387de-80a1-11ee-b962-0242ac120002")
    var bluetoothAdapter: BluetoothAdapter? = null
    var pm: PermissionsManager? = null

    val isConnected = mutableStateOf(false)
    private val deviceHC05 = connectToHC()

    fun sendMessageToHC(message: String) {
        if (pm?.checkBtPermission() == true) {
            val socket: BluetoothSocket? =
                deviceHC05?.createRfcommSocketToServiceRecord(MY_UUID)
            if (socket != null) {
                if (socket.isConnected) {
                    val outputStream = socket.outputStream
                    outputStream.write(message.toByteArray())
                    outputStream.flush()
                }
            }
        }
    }

     fun connectToHC(): BluetoothDevice? {
         Log.d("kilo", "bluetoothAdapter?.isEnabled: ${bluetoothAdapter?.isEnabled}")
         Log.d("kilo", "pm?.checkBtPermission() : ${pm?.checkBtPermission() }")
        if (bluetoothAdapter?.isEnabled == true && pm?.checkBtPermission() == true) {
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

            if (pairedDevices != null) {
                for (device in pairedDevices) {
                    Log.d("kilo", device.name)
                    if (device.name == "HC-05") {
                        isConnected.value = true
                        return device
                    }
                }
            }
        }
        isConnected.value = false
        return null
    }
}