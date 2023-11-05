package com.example.plantingapp

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.plantingapp.ui.components.TabNavigationItem
import com.example.plantingapp.ui.screens.camera.CameraTab
import com.example.plantingapp.ui.screens.explore.ExploreTab
import com.example.plantingapp.ui.screens.home.HomeTab
import com.example.plantingapp.ui.screens.profile.ProfileTab
import com.example.plantingapp.ui.screens.saved.SavedTab
import com.example.plantingapp.ui.theme.PlantingAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.io.File
import java.util.concurrent.ExecutorService


class MainActivity : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var photoUri: Uri
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)


    private fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        shouldShowCamera.value = false
        photoUri = uri
        shouldShowPhoto.value = true
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("kilo", "Permission granted")
        } else {
            Log.i("kilo", "Permission denied")
            /*Toast.makeText(context,
                "CameraState: Pending Open",
                Toast.LENGTH_SHORT).show()*/

        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                shouldShowCamera = mutableStateOf(true)
                Log.i("kilo", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("kilo", this.resources.configuration.screenWidthDp.toString())
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        requestCameraPermission()

        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                // set transparent color so that our image is visible
                // behind the status bar
                systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
                systemUiController.setNavigationBarColor(color = Color.Transparent)
            }
            TabNavigator(HomeTab) {
                //PlantingAppTheme {
                    Scaffold(
                        content = {
                            CurrentTab()
                        },
                        bottomBar = {
                            NavigationBar(
                                containerColor = Color.White,
                                modifier = Modifier
                                    .shadow(100.dp)
                            ) {
                                TabNavigationItem(HomeTab,
                                    painterResource(R.drawable.ic_home_selected),
                                    painterResource(R.drawable.ic_home_unselected)
                                )
                                TabNavigationItem(ExploreTab,
                                    painterResource(R.drawable.ic_explore_selected),
                                    painterResource(R.drawable.ic_explore_unselected))
                                TabNavigationItem(CameraTab,
                                    painterResource(R.drawable.ic_scan),
                                    painterResource(R.drawable.ic_scan))
                                TabNavigationItem(SavedTab,
                                    painterResource(R.drawable.ic_saved_selected),
                                    painterResource(R.drawable.ic_saved_unselected))
                                TabNavigationItem(ProfileTab,
                                    painterResource(R.drawable.ic_profile_selected),
                                    painterResource(R.drawable.ic_profile_unselected))
                            }
                        }
                    )
             //   }
            }
            /*outputDirectory = getOutputDirectory()
            cameraExecutor = Executors.newSingleThreadExecutor()
            if (shouldShowCamera.value) {
                CameraScreen(
                    outputDirectory = outputDirectory,
                    executor = cameraExecutor,
                    onImageCaptured = ::handleImageCapture,
                    onError = { Log.e("kilo", "View error:", it) }
                )
            }
            if (shouldShowPhoto.value) {
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }*/
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlantingAppTheme {
        Greeting("Android")
    }
}