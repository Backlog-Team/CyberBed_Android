package com.example.plantingapp.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantingapp.ui.components.NicknameField
import com.example.plantingapp.ui.components.ScanButton
import com.example.plantingapp.ui.components.TabHeader

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TabHeader(title = "Profile")
        ScanButton()
        NicknameField()
    }
}