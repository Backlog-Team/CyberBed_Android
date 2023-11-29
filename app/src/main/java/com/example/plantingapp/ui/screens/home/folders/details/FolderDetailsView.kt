package com.example.plantingapp.ui.screens.home.folders.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.ui.states.LoadingStates
import com.example.plantingapp.ui.components.PlantCard
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel

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
                    text = "Folder: ${folder.folderName}",
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
                        LazyColumn {
                            items(plants.value.size) { index ->
                                PlantCard(
                                    plants.value[index]
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