package com.example.plantingapp.ui.screens.settings.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantingapp.domain.models.Channel
import com.example.plantingapp.management.PermissionsManager
import com.example.plantingapp.utils.Constants
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
    var socket: BluetoothSocket? = null

    private var _toast = MutableStateFlow("")
    var toast: StateFlow<String> = _toast

    fun sendChannel1() {
        sendMessageToHC("Channel_1")
    }

    fun sendChannel2() {
        sendMessageToHC("Channel_2")
    }

    fun sendChannel(channel: Channel) {
        sendMessageToHC("Channel_${channel.channelID}")
    }

    fun connectToSocket() {
        if (pm?.checkBtPermission() == true) {
            if (deviceHC05 != null) {
                val bluetoothSocket: BluetoothSocket? =
                    deviceHC05!!.createInsecureRfcommSocketToServiceRecord(MY_UUID)
                Log.d(Constants.DEBUG_TAG, "Device: ${deviceHC05?.name ?: "-"}")
                Log.d(
                    Constants.DEBUG_TAG,
                    "Socket.isConnected: ${bluetoothSocket?.isConnected ?: "doesn't exist"}"
                )
                if (bluetoothSocket != null && deviceHC05 != null) {
                    try {
                        if (!bluetoothSocket.isConnected)
                            bluetoothSocket.connect()
                        Log.d(
                            Constants.DEBUG_TAG,
                            "BTSocket.isConnected: ${bluetoothSocket.isConnected}"
                        )
                        if (bluetoothSocket.isConnected) {
                            socket = bluetoothSocket
                        }
                    } catch (exception: IOException) {
                        bluetoothSocket.close()
                        _toast.value = "Ошибка подключения к сокету"

                        Log.e(
                            Constants.DEBUG_TAG,
                            "IOException при подключении сокета: ${exception}"
                        )
                    } catch (exception: Exception) {
                        bluetoothSocket.close()
                        Log.e(Constants.DEBUG_TAG, "Exception при подключении сокета: ${exception}")
                    } catch (exception: Error) {
                        bluetoothSocket.close()
                        Log.e(Constants.DEBUG_TAG, "Ошибка при подключении сокета: ${exception}")
                    }
                }
            }
        }
    }

    fun sendMessageToHC(message: String) {
        if (deviceHC05 != null && socket != null) {
            val outputStream = socket!!.outputStream
            Log.d(Constants.DEBUG_TAG, "Writing data...")
            outputStream.write(message.toByteArray())
            Log.d(Constants.DEBUG_TAG, "Wrote data: ${message}")
            _toast.value = "Сигнал отправлен"

            outputStream.flush()
        }
    }

    fun getMessageFromHC(message: String) {
        if (deviceHC05 != null && socket != null) {
            val outputStream = socket!!.outputStream
            Log.d(Constants.DEBUG_TAG, "Writing data...")
            outputStream.write(message.toByteArray())
            Log.d(Constants.DEBUG_TAG, "Wrote data: ${message}")
            _toast.value = "Сигнал отправлен"

            outputStream.flush()
        }
    }
    fun getPairedDevices() {
        Log.d(Constants.DEBUG_TAG, "bluetoothAdapter?.isEnabled: ${bluetoothAdapter?.isEnabled}")
        Log.d(Constants.DEBUG_TAG, "pm?.checkBtPermission() : ${pm?.checkBtPermission()}")
        if (bluetoothAdapter?.isEnabled == true && pm?.checkBtPermission() == true) {
            pairedDevices = bluetoothAdapter?.bondedDevices ?: setOf()
            for (device in pairedDevices) {
                Log.d(Constants.DEBUG_TAG, device.name)
            }
        }
    }

     fun connectToHC(name: String) {
         Log.d(Constants.DEBUG_TAG, "bluetoothAdapter?.isEnabled: ${bluetoothAdapter?.isEnabled}")
         Log.d(Constants.DEBUG_TAG, "pm?.checkBtPermission() : ${pm?.checkBtPermission()}")
         getPairedDevices()

         for (device in pairedDevices) {
             Log.d(Constants.DEBUG_TAG, device.name)
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
        connectToSocket()
    }
}