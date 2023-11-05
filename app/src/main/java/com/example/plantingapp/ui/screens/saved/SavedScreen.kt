package com.example.plantingapp.ui.screens.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantingapp.ui.components.ScanButton
import com.example.plantingapp.ui.components.TabHeader

@Composable
fun SavedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TabHeader(title = "Saved")
        ScanButton()
    }
}