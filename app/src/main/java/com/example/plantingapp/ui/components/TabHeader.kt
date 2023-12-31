package com.example.plantingapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
        )/*
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                //TODO(onClick)
                onClick = {
                    Log.i(Constants.DEBUG_TAG, "Search clicked")
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
                    Log.i(Constants.DEBUG_TAG, "Add clicked")
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
        }*/
    }
}