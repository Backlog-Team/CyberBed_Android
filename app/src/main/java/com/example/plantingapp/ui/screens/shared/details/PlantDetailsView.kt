package com.example.plantingapp.ui.screens.shared.details

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.plantingapp.ui.components.dialogs.SetNotification
import com.example.plantingapp.ui.screens.folders.FoldersViewModel
import com.example.plantingapp.ui.screens.saved.SavedViewModel
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothViewModel
import com.example.plantingapp.ui.theme.BluePrimary
import com.example.plantingapp.ui.theme.GreenPrimary
import com.example.plantingapp.ui.theme.VioletPrimary
import com.example.plantingapp.ui.theme.YellowPrimary
import com.example.plantingapp.utils.Constants.DEBUG_TAG
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PlantDetailsView(
    plant: Plant,
    isSaved: Boolean = false,
    folder: Folder? = null
) {
    val navigator = LocalNavigator.currentOrThrow
    val savedViewModel: SavedViewModel = getViewModel()
    val foldersViewModel: FoldersViewModel = getViewModel()


    val textBoxMinHeight = 100.dp
    var textBoxMaxHeight = 0.dp
    val localDensity = LocalDensity.current

    var textBoxHeight by remember { mutableStateOf(textBoxMinHeight) }
    val channel = savedViewModel.channel.collectAsState()

    val btViewModel: BluetoothViewModel = getViewModel()
    NestedView(
        isDarkBackground = true, onClose = { navigator.pop() },
        modifier = Modifier.background(Color.Black)
    ) {

        PlantImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            imageUrl = "https://zenehu.space/api/search/plants/${plant.id}/image"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(0.2f))
                .padding(top = 250.dp)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = plant.displayPid ?: stringResource(id = R.string.empty),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)
            )
            Text(
                text = stringResource(id = R.string.description),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Box {
                Column(
                    modifier = Modifier
                        .onPlaced { coordinates ->
                            textBoxMaxHeight = with(localDensity) { coordinates.size.height.toDp() }
                        })
                {
                    Log.d(DEBUG_TAG, "$textBoxMinHeight-$textBoxMaxHeight")
                    Text(
                        text = plant.basic?.floralLanguage ?: stringResource(id = R.string.empty),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.blooming),
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)

                    )
                    Text(
                        text = plant.basic?.blooming ?: stringResource(id = R.string.empty),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
                Text(
                    text = if (textBoxHeight == textBoxMinHeight) "Подробнее" else "Скрыть",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.1f),
                                    Color.White.copy(alpha = 0.8f), Color.White
                                )
                            )
                        )
                        .padding(top = 20.dp)
                        .padding(horizontal = 10.dp)
                        .align(Alignment.BottomStart)
                        .clickable {
                            textBoxHeight =
                                if (textBoxHeight == textBoxMinHeight) textBoxMaxHeight + 20.dp
                                else textBoxMinHeight
                            Log.d(DEBUG_TAG, "$textBoxMinHeight-$textBoxMaxHeight, $textBoxHeight")
                        }
                )
            }
            Divider(Modifier.height(1.dp))
            Text(
                text = stringResource(R.string.recommendations),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ruler),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(id = R.string.size),
                            color = GreenPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = plant.maintenance?.size ?: stringResource(id = R.string.empty),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_waterdrop),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.humidity),
                            color = BluePrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (plant.parameter?.minSoilMoisture != plant.parameter?.maxSoilMoisture)
                                "${plant.parameter?.minSoilMoisture}-${plant.parameter?.maxSoilMoisture}%"
                            else "${plant.parameter?.minSoilMoisture}%",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_sun),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.light),
                            color = YellowPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text =
                            if (plant.parameter?.minLightLux != plant.parameter?.maxLightLux)
                                "${plant.parameter?.minLightLux}-${plant.parameter?.maxLightLux} люкс"
                            else
                                "${plant.parameter?.minLightLux} люкс",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_temp),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier.padding(5.dp)
                    )
                    Column {
                        Text(
                            text = stringResource(R.string.temperature),
                            color = VioletPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (plant.parameter?.minTemp != plant.parameter?.maxTemp)
                                "${plant.parameter?.minTemp}-${plant.parameter?.maxTemp}°С"
                            else
                                "${plant.parameter?.minTemp}%",
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Row (
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (!isSaved) {
                    val showDialog = remember { mutableStateOf(false) }
                    Button(
                        onClick = { showDialog.value = !showDialog.value },
                        modifier = Modifier.padding(horizontal = 10.dp),
                    ) {
                        Text(stringResource(id = R.string.add))
                    }
                    if (showDialog.value) {
                        SetNotification(
                            plant = plant,
                            setShowDialog = {
                                showDialog.value = it
                            }
                        )
                    }
                }
                if (folder == null && !isSaved) {
                    Button(
                        onClick = { foldersViewModel.addPlantToFolder(foldersViewModel.folders.value[0],
                            plant, "") },
                        modifier = Modifier.padding(horizontal = 10.dp),
                    ) {
                        Text("Хочу")
                    }
                }
            }
            if (isSaved) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    onClick = {
                        savedViewModel.getChannel(plant)
                        btViewModel.sendChannel(channel.value)
                    }
                ) {
                    Text(stringResource(R.string.water_plant))
                }
            }
        }
    }
}