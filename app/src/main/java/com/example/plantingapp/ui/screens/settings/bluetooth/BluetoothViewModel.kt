package com.example.plantingapp.ui.screens.settings.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantingapp.management.PermissionsManager
import java.io.IOException
import java.util.UUID


const val REQUEST_ENABLE_BT = 1

class BluetoothViewModel: ViewModel() {
    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    var bluetoothAdapter: BluetoothAdapter? = null
    var pm: PermissionsManager? = null

    val isConnected = mutableStateOf(false)
    var pairedDevices: Set<BluetoothDevice>? = setOf()
    private var deviceHC05: BluetoothDevice? = null

    fun sendMessageToHC(message: String) {
        if (pm?.checkBtPermission() == true) {
            val socket: BluetoothSocket? =
                deviceHC05?.createInsecureRfcommSocketToServiceRecord(MY_UUID)
            Log.d("kilo", "Device: ${deviceHC05?.name ?: "-"}")
            Log.d("kilo", "Socket.isConnected: ${socket?.isConnected ?: "doesn't exist"}")
            if (socket != null && deviceHC05 != null) {
                try {
                    val bluetoothSocket: BluetoothSocket? = deviceHC05!!.javaClass.getMethod(
                        "createRfcommSocket", Int::class.javaPrimitiveType
                    ).invoke(deviceHC05, 1) as BluetoothSocket?
                    Log.d(
                        "kilo",
                        "BTSocket.isConnected: ${bluetoothSocket?.isConnected ?: "doesn't exist"}"
                    )
                    if (bluetoothSocket != null) {
                        bluetoothSocket.connect()
                        Log.d(
                            "kilo",
                            "BTSocket.isConnected: ${bluetoothSocket.isConnected}"
                        )
                        if (bluetoothSocket.isConnected) {
                            Log.d("kilo", "Open output stream")
                            val outputStream = bluetoothSocket.outputStream
                            Log.d("kilo", "Writing data...")
                            outputStream.write(message.toByteArray())
                            Log.d("kilo", "Wrote data")
                            outputStream.flush()
                        }
                    }
                } catch (exception: IOException){
                    socket.close()
                    Log.e("kilo", "Ошибка при подключении сокета: ${exception}")
                }
            }
        }
    }

     fun connectToHC(name: String) {
         Log.d("kilo", "bluetoothAdapter?.isEnabled: ${bluetoothAdapter?.isEnabled}")
         Log.d("kilo", "pm?.checkBtPermission() : ${pm?.checkBtPermission() }")
        if (bluetoothAdapter?.isEnabled == true && pm?.checkBtPermission() == true) {
            pairedDevices = bluetoothAdapter?.bondedDevices

            if (pairedDevices != null) {
                for (device in pairedDevices!!) {
                    Log.d("kilo", device.name)
                    if (device.name == name) {
                        isConnected.value = true
                        deviceHC05 = device
                        break
                    }
                }
            }
        }
    }
}