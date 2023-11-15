package com.example.plantingapp.management

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsManager(
    private val activity: ComponentActivity
) {
    fun checkBtPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else false
    }

    fun checkCameraPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        } else false
    }
    fun requestBtPermission() {
        when {
            checkBtPermission() -> {
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) -> Log.i("kilo", "Show permissions dialog")

            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(
                            android.Manifest.permission.BLUETOOTH,
                            android.Manifest.permission.BLUETOOTH_CONNECT,
                            android.Manifest.permission.BLUETOOTH_ADMIN
                        ),
                        requestCode
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(
                            android.Manifest.permission.BLUETOOTH,
                            android.Manifest.permission.BLUETOOTH_ADMIN
                        ),
                        requestCode
                    )
                    Toast.makeText(
                        activity,
                        "Permission BLUETOOTH_CONNECT requires API level 31",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun requestCameraPermission() {
        when {
            checkCameraPermission() -> {
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                android.Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show permissions dialog")

            else -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.CAMERA),
                    requestCode
                )
            }
        }
    }


    companion object {
        private const val requestCode = 92
    }
}