package com.example.plantingapp.ui.screens.shared.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.components.dialogs.FoldersMenu
import com.example.plantingapp.ui.components.PlantImage
import com.example.plantingapp.ui.components.containers.NestedView
import com.example.plantingapp.ui.components.dialogs.ChooseChannel
import com.example.plantingapp.ui.theme.BluePrimary
import com.example.plantingapp.ui.theme.GreenPrimary
import com.example.plantingapp.ui.theme.VioletPrimary
import com.example.plantingapp.ui.theme.YellowPrimary

@Composable
fun PlantDetailsView(
    plant: Plant,
    isSaved: Boolean = false,
    folder: Folder? = null
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
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
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
            Row {
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ruler),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.padding(5.dp)
                        )
                        Column {
                            Text(
                                text = "Высота",
                                color = GreenPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = plant.maintenance?.size ?: "-",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_waterdrop),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.padding(5.dp)
                        )
                        Column {
                            Text(
                                text = "Влажность почвы",
                                color = BluePrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${plant.parameter?.minSoilMoisture}-" +
                                        "${plant.parameter?.maxSoilMoisture}%",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sun),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.padding(5.dp)
                        )
                        Column {
                            Text(
                                text = "Свет",
                                color = YellowPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text =
                                if (plant.parameter?.minLightLux != plant.parameter?.minLightLux)
                                    "${plant.parameter?.minLightLux}-${plant.parameter?.minLightLux} люкс"
                                else
                                    "${plant.parameter?.minLightLux} люкс",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_temp),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.padding(5.dp)
                        )
                        Column {
                            Text(
                                text = "Влажность воздуа",
                                color = VioletPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (plant.parameter?.minEnvHumidity != plant.parameter?.maxEnvHumidity)
                                    "${plant.parameter?.minEnvHumidity}-${plant.parameter?.maxEnvHumidity}%"
                                else
                                    "${plant.parameter?.maxEnvHumidity}%",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

            }
            Row {
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

                val chooseChannel = remember { mutableStateOf(false) }

                Button(
                    onClick = { chooseChannel.value = !chooseChannel.value },
                    modifier = Modifier
                        .width(180.dp)
                        .padding(horizontal = 10.dp),
                ) {
                    Text("Полить растение")
                }
                if (chooseChannel.value) {
                    ChooseChannel(
                        setShowDialog = {
                            chooseChannel.value = it
                        }
                    )
                }
            }
        }
    }
}
