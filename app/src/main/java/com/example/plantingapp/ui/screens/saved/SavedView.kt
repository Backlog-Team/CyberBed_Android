package com.example.plantingapp.ui.screens.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.R
import com.example.plantingapp.ui.states.LoadingStates
import com.example.plantingapp.ui.components.cards.PlantCard
import com.example.plantingapp.ui.components.containers.TabView

@Composable
fun SavedView(
    viewModel: SavedViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.getSavedPlants()
    }
    val loadingState = viewModel.loadingStates.collectAsState()
    val plants by viewModel.plants.collectAsState()


    TabView {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.saved),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                IconButton(
                    onClick = { viewModel.getSavedPlants() },
                    content = {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Refresh),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
            }

            when (loadingState.value) {
                LoadingStates.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        if (plants.isEmpty()) {
                            Text(stringResource(R.string.empty_list))
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(plants.size) { index ->
                                    val saved = remember { mutableStateOf(true) }
                                    PlantCard(plant = plants[index], true)
                                }
                            }
                        }
                    }
                }

                LoadingStates.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(stringResource(R.string.saved_plants_loading_error))
                        Button(onClick = { viewModel.getSavedPlants() }) {
                            Text(stringResource(id = R.string.retry))
                        }
                    }
                }

                LoadingStates.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                LoadingStates.NotLoading -> {
                }
            }
        }
    }
}
