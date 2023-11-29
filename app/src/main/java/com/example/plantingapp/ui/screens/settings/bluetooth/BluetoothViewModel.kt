package com.example.plantingapp.ui.screens.settings.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantingapp.management.PermissionsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.util.UUID


const val REQUEST_ENABLE_BT = 1

class BluetoothViewModel: ViewModel() {
    private val MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    var bluetoothAdapter: BluetoothAdapter? = null
    var pm: PermissionsManager? = null

    val isConnected = mutableStateOf(false)
    var pairedDevices: Set<BluetoothDevice> = setOf()
    var deviceHC05: BluetoothDevice? = null

    private var _message = MutableStateFlow("")
    var message: StateFlow<String> = _message

    fun sendMessageToHC(message: String) {
        if (pm?.checkBtPermission() == true) {
            val bluetoothSocket: BluetoothSocket? =
                deviceHC05?.createInsecureRfcommSocketToServiceRecord(MY_UUID)
            Log.d("kilo", "Device: ${deviceHC05?.name ?: "-"}")
            Log.d("kilo", "Socket.isConnected: ${bluetoothSocket?.isConnected ?: "doesn't exist"}")
            if (bluetoothSocket != null && deviceHC05 != null) {
                try {
                    if (!bluetoothSocket.isConnected)
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
                        _message.value = "Данные отправлены"

                        outputStream.flush()
                    }
                    bluetoothSocket.close()
                } catch (exception: IOException) {
                    bluetoothSocket.close()
                    Log.e("kilo", "IOException при подключении сокета: ${exception}")
                } catch (exception: Exception) {
                    bluetoothSocket.close()
                    Log.e("kilo", "Exception при подключении сокета: ${exception}")
                } catch (exception: Error) {
                    bluetoothSocket.close()
                    Log.e("kilo", "Ошибка при подключении сокета: ${exception}")
                }
            }
        }
        _message.value = ""

    }

    fun getPairedDevices() {
        Log.d("kilo", "bluetoothAdapter?.isEnabled: ${bluetoothAdapter?.isEnabled}")
        Log.d("kilo", "pm?.checkBtPermission() : ${pm?.checkBtPermission()}")
        if (bluetoothAdapter?.isEnabled == true && pm?.checkBtPermission() == true) {
            pairedDevices = bluetoothAdapter?.bondedDevices ?: setOf()
            for (device in pairedDevices) {
                Log.d("kilo", device.name)
            }
        }
    }

     fun connectToHC(name: String) {
         Log.d("kilo", "bluetoothAdapter?.isEnabled: ${bluetoothAdapter?.isEnabled}")
         Log.d("kilo", "pm?.checkBtPermission() : ${pm?.checkBtPermission()}")
         getPairedDevices()

         for (device in pairedDevices) {
             Log.d("kilo", device.name)
             if (device.name == name) {
                 isConnected.value = true
                 deviceHC05 = device
                 break
             }
         }
     }

    init {
        getPairedDevices()
        connectToHC("HC-05")
    }
}