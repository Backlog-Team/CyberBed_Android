package com.example.plantingapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.R

@Composable
fun TabHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
            //TODO(fontFamily)
        )
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                //TODO(onClick)
                onClick = {
                    Log.i("kilo", "Search clicked")
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            )
            IconButton(
                //TODO(onClick)
                onClick = {
                    Log.i("kilo", "Add clicked")
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            )
        }
    }
}