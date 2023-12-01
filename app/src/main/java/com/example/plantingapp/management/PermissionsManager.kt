package com.example.plantingapp.management

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plantingapp.utils.Constants

class PermissionsManager(
    private val activity: ComponentActivity
) {
    fun checkBtPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun checkCameraPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                activity,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun requestBtPermission() {
        when {
            checkBtPermission() -> {
                Log.i(Constants.DEBUG_TAG, "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) -> Log.i(Constants.DEBUG_TAG, "Show permissions dialog")

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
                }
            }
        }
    }

    fun requestCameraPermission() {
        when {
            checkCameraPermission() -> {
                Log.i(Constants.DEBUG_TAG, "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                android.Manifest.permission.CAMERA
            ) -> Log.i(Constants.DEBUG_TAG, "Show permissions dialog")

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