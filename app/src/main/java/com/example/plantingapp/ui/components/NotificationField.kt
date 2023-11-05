package com.example.plantingapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.R
import com.example.plantingapp.ui.theme.TextSecondary

@Composable
fun NotificationField(img: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = img,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.CenterVertically)
        )
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Water your Cactus today (living room)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                //TODO(fontFamily)
            )
            Text(
                text = "It’s 2 weeks old, you have to water it twice a w...",
                fontSize = 12.sp,
                color = TextSecondary
                //TODO(fontFamily)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Read more",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically),
            tint = Color.Unspecified
        )
    }
}