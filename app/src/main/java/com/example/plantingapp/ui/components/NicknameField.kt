package com.example.plantingapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.plantingapp.R
import com.example.plantingapp.ui.theme.GrayPrimary
import com.example.plantingapp.ui.theme.GreenBackground
import com.example.plantingapp.ui.theme.GreenPrimary

@Composable
fun NicknameField() {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(GrayPrimary),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nickname",
            modifier = Modifier.padding(10.dp))
    }
}