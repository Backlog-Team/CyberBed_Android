package com.example.plantingapp.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.R

@Composable
@Preview(showBackground = true)
fun HomeScreen(
    //modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 50.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
                Text(
                    text = "My Plants",
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
                            contentDescription = "Take picture",
                            tint = Color.Gray,
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
                            contentDescription = "Take picture",
                            tint = Color.Gray,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(Color.Green.copy(alpha = 0.2f)),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_scan),
                contentDescription = "scan",
                tint = Color.Green,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Scan and identify the plant",
                color = Color.Green,
                modifier = Modifier.padding(10.dp))
        }
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Categories",
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                //TODO(fontFamily)
            )
            Text(
                text = "View all",
                color = Color.Green,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                //TODO(fontFamily)
            )
        }
    }
}