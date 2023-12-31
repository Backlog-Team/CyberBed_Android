package com.example.plantingapp.ui.components.cards

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.plantingapp.R
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.components.PlantImage
import com.example.plantingapp.ui.components.dialogs.FoldersMenu
import com.example.plantingapp.ui.screens.folders.FoldersViewModel
import com.example.plantingapp.ui.screens.saved.SavedViewModel
import com.example.plantingapp.ui.screens.shared.details.PlantDetailsScreen
import com.example.plantingapp.ui.theme.BluePrimary
import com.example.plantingapp.ui.theme.GrayBackground
import com.example.plantingapp.ui.theme.GreenPrimary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun PlantCard(
    plant: Plant,
    saved: Boolean = false,
    folder: Folder? = null
) {
    val navigator = LocalNavigator.current
    val isSaved by remember { mutableStateOf(saved) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { navigator?.push(PlantDetailsScreen(plant, isSaved, folder)) },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = GrayBackground
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = plant.displayPid ?: stringResource(id = R.string.empty),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ruler),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.padding(5.dp)
                        )
                        Column {
                            Text(
                                text = stringResource(R.string.size),
                                color = GreenPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = plant.maintenance?.size
                                    ?: stringResource(id = R.string.empty),
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_waterdrop),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.padding(5.dp)
                        )
                        Column {
                            Text(
                                text = stringResource(id = R.string.humidity),
                                color = BluePrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (plant.parameter?.minSoilMoisture != plant.parameter?.maxSoilMoisture)
                                    "${plant.parameter?.minSoilMoisture}-${plant.parameter?.maxSoilMoisture}%"
                                else "${plant.parameter?.minSoilMoisture}%",
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
                PlantImage(
                    modifier = Modifier
                        .weight(4f)
                        .padding(vertical = 15.dp, horizontal = 5.dp)
                        .aspectRatio(ratio = 1f)
                        .clip(RoundedCornerShape(5.dp)),
                    imageUrl = "https://zenehu.space/api/search/plants/${plant.id}/image"
                )
            }
            if (isSaved)
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Bookmark),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                )

            /*            val showDialog = remember { mutableStateOf(false) }

            IconButton(
                onClick = { showDialog.value = !showDialog.value },
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.BottomEnd)
                    .size(20.dp)
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.Add),
                    contentDescription = null
                )
            }

            if (showDialog.value) {
                FoldersMenu(
                    plant = plant,
                    setShowDialog = {
                        showDialog.value = it
                    }
                )
            }
            if (isSaved && folder != null) {
                /*val showFolders = remember { mutableStateOf(false) }

                if (showFolders.value) {
                    FoldersAdded(
                        plant = plant,
                        setShowDialog = {
                            showFolders.value = it
                        }
                    )
                }*/

                IconButton(
                    onClick = {
                        folderViewModel.delPlantsFromFolder(folder, plant)
                        folderViewModel.getFolders()
                        //showFolders.value = !showFolders.value
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.BottomStart)
                        .size(20.dp)
                ) {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Default.Delete),
                        contentDescription = null
                    )
                }
            }*/
        }
    }
}
