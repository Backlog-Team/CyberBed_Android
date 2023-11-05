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
import com.example.plantingapp.ui.theme.GreenBackground
import com.example.plantingapp.ui.theme.GreenPrimary


@Composable
fun ScanButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(GreenBackground),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_scan),
            contentDescription = "Scan",
            tint = GreenPrimary,
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = "Scan and identify the plant",
            color = GreenPrimary,
            modifier = Modifier.padding(10.dp))
    }
}