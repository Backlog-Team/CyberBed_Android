package com.example.plantingapp.ui.screens.scan


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.koin.androidx.compose.getViewModel

object ScanTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Scan"
            val icon = rememberVectorPainter(Icons.Default.Camera)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = getViewModel<ScanViewModel>()
        Navigator(CameraScreen(screenModel))
    }
}