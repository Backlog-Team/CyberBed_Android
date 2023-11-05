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
import androidx.compose.material3.Divider
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
import com.example.plantingapp.ui.components.GroupHeader
import com.example.plantingapp.ui.components.NotificationField
import com.example.plantingapp.ui.components.ScanButton
import com.example.plantingapp.ui.components.TabHeader
import com.example.plantingapp.ui.theme.GreenBackground
import com.example.plantingapp.ui.theme.GreenPrimary

@Composable
@Preview(showBackground = true)
fun HomeScreen(
    //modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TabHeader(title = "My Plants")
        ScanButton()
        GroupHeader("Categories")
        Divider(color = Color.LightGray, thickness = 1.dp)
        GroupHeader(title = "Alerts for today")
        NotificationField(img = painterResource(id = R.drawable.img_cactus))
        NotificationField(img = painterResource(id = R.drawable.img_bamboo))
    }
}