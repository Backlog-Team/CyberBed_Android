package com.example.plantingapp.ui.screens.shared.details

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.R
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.components.FoldersMenu
import com.example.plantingapp.ui.components.PlantImage
import com.example.plantingapp.ui.components.containers.NestedView

@Composable
fun PlantDetailsView(
    plant: Plant
) {
    val navigator = LocalNavigator.currentOrThrow
    NestedView(onClose = { navigator.pop() }) {

        PlantImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp),
            imageUrl = "https://zenehu.space/api/search/plants/${plant.id}/image"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp)
                .clip(RoundedCornerShape(0.2f))
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = plant.displayPid ?: "Название не указано",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
            )
            Text(
                text = "Описание",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 10.dp)

            )
            Text(
                text = plant.basic?.floralLanguage ?: "Описание не указано",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Text(
                text = "Цветение",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 10.dp)

            )
            Text(
                text = plant.basic?.blooming ?: "Описание не указано",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),

                ) {


                Text(
                    text = plant.basic?.blooming ?: "Описание не указано",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        modifier = Modifier.height(50.dp),
                        painter = painterResource(id = R.drawable.ic_sun),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text("${plant.parameter?.minLightLux}-${plant.parameter?.minLightLux}")
                }
                Text("Влажность воздуха")
                Row {
                    Icon(
                        modifier = Modifier.height(50.dp),
                        painter = painterResource(id = R.drawable.ic_temp),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text("${plant.parameter?.minEnvHumidity}-${plant.parameter?.maxEnvHumidity}")
                }
            }
        }

        val showDialog = remember { mutableStateOf(false) }
        Button(
            onClick = { showDialog.value = !showDialog.value },
            modifier = Modifier
                .width(180.dp)
                .padding(horizontal = 10.dp),
        ) {
            Text("Добавить")
        }
        if (showDialog.value) {
            FoldersMenu(
                plant = plant,
                setShowDialog = {
                    showDialog.value = it
                }
            )
        }
    }
}