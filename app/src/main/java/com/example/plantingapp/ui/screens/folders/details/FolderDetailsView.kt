package com.example.plantingapp.ui.screens.folders.details

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.R
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.ui.states.LoadingStates
import com.example.plantingapp.ui.components.cards.PlantCard
import com.example.plantingapp.ui.components.containers.ItemsList
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.components.dialogs.ChooseFolder
import com.example.plantingapp.ui.components.dialogs.SetNotification
import com.example.plantingapp.ui.screens.folders.FoldersViewModel
import com.example.plantingapp.ui.screens.folders.card.FolderCard
import com.example.plantingapp.utils.Constants

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FolderDetailsView(
    folder: Folder,
    viewModel: FoldersViewModel

) {
    LaunchedEffect(Unit) {
        viewModel.getPlantFromFolder(folder)
    }
    val loadingStates = viewModel.loadingState.collectAsState()
    val plants = viewModel.plants.collectAsState()


    TabView {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.folder, folder.folderName ?:
                    stringResource(R.string.empty)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

            }
            Box {
                when (loadingStates.value) {
                    LoadingStates.Loading -> {
                        CircularProgressIndicator()
                    }

                    LoadingStates.Error -> {
                        Text("Error")
                    }

                    LoadingStates.Success -> {

                        ItemsList({ viewModel.getFolders() }) {
                            items(plants.value.size) { index ->
                                val showChooseFolder = remember {mutableStateOf(false)}
                                if (showChooseFolder.value) {
                                    ChooseFolder(
                                        folder,
                                        plants.value[index]
                                    ) { showChooseFolder.value = it }
                                }
                                val dismissState = rememberDismissState()

                                // check if the user swiped
                                if (dismissState.isDismissed(direction = DismissDirection.StartToEnd)) {
                                    viewModel.delPlantsFromFolder(folder, plants.value[index])
                                    viewModel.getPlantFromFolder(folder)

                                }
                                else if (dismissState.isDismissed(direction = DismissDirection.EndToStart)) {
                                    showChooseFolder.value = !showChooseFolder.value
                                    viewModel.getPlantFromFolder(folder)

                                }
                                SwipeToDismiss(
                                    state = dismissState,
                                    directions = setOf(
                                        DismissDirection.EndToStart,
                                        DismissDirection.StartToEnd,
                                        ),
                                    background = {
                                        // background color
                                        val backgroundColor by animateColorAsState(
                                            when (dismissState.targetValue) {
                                                DismissValue.DismissedToEnd -> Color.Red.copy(alpha = 0.8f)
                                                DismissValue.DismissedToStart -> Color.Blue.copy(alpha = 0.2f)
                                                else -> Color.White
                                            }, label = ""
                                        )

                                        // icon size
                                        val iconScale by animateDpAsState(
                                            targetValue =
                                            if (dismissState.targetValue ==
                                                DismissValue.DismissedToEnd || dismissState.targetValue ==
                                                DismissValue.DismissedToStart) 30.dp else 20.dp,
                                            label = ""
                                        )

                                        Box(
                                            Modifier
                                                .fillMaxSize()
                                                .background(color = backgroundColor)
                                                .padding(end = 16.dp), // inner padding
                                        ) {
                                            if (dismissState.targetValue == DismissValue.DismissedToEnd)
                                            Icon(
                                                modifier = Modifier
                                                    .size(iconScale)
                                                    .align(Alignment.CenterStart),
                                                imageVector = Icons.Outlined.Delete,
                                                contentDescription = null,
                                                tint = Color.Black
                                            )
                                            else if (dismissState.targetValue == DismissValue.DismissedToStart)
                                            Icon(
                                                modifier = Modifier
                                                    .size(iconScale)
                                                    .align(Alignment.CenterEnd),
                                                painter = painterResource(id = R.drawable.ic_folder_replace),
                                                contentDescription = null,
                                                tint = Color.Black
                                            )
                                        }
                                    },
                                    dismissContent = {
                                        // list item
                                        PlantCard(plants.value[index])
                                    }
                                )
                            }
                        }
                    }

                    else -> {
                        Text("Loading hasn't started yet")
                    }
                }
            }
        }
    }
}