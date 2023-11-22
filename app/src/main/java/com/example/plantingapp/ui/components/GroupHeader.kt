package com.example.plantingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.ui.theme.GreenPrimary

@Composable
fun GroupHeader(title: String, onViewAll: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            fontWeight = FontWeight.Medium,
            //TODO(fontFamily)
        )
        Text(
            text = "View all",
            color = GreenPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.CenterVertically)
            //TODO(fontFamily)
        )
    }
}