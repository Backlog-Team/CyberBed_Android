package com.example.plantingapp.ui.screens.shared.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.explore.ExploreViewModel

@Composable
fun PlantDetailsView(
    plant: Plant,
    viewModel: ExploreViewModel,
    imageRequest: ImageRequest
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = plant.displayPid ?: "Название не указано",
                    modifier = Modifier.width(180.dp)
                )
                Button(
                    onClick = {TODO()},
                    modifier = Modifier.width(180.dp),
                ) {
                    Text("Добавить")
                }
            }
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
        }
        Text(plant.species?.blooming ?: "Описание цветов не указано")
        Text(plant.maintenance?.watering ?: "Режим полива не указан")


    }
}