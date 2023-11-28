package com.example.plantingapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.plantingapp.domain.models.CustomPlant
import com.example.plantingapp.ui.theme.GrayBackground

@Composable
fun CustomPlantCard(customPlant: CustomPlant) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(300.dp)
            .height(200.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = GrayBackground
        ),
    ){
        Row {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(customPlant.plantName ?: "Без названия")
                Text(customPlant.about ?: "Описание не указано")
            }
            PlantImage(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(vertical = 15.dp, horizontal = 5.dp)
                    .aspectRatio(ratio = 1f)
                    .clip(RoundedCornerShape(5.dp)),
                imageUrl = "https://zenehu.space/api/custom/plants/${customPlant.id}/image"
            )
        }
    }
}

