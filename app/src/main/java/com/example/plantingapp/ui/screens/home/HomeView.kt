package com.example.plantingapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantingapp.R
import com.example.plantingapp.ui.components.cards.PlantCard
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.screens.saved.SavedViewModel
import com.example.plantingapp.ui.states.LoadingStates

@Composable
fun HomeView(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.my_plants),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }


            /*Divider(color = Color.LightGray, thickness = 1.dp)
            GroupHeader(stringResource(R.string.notifications)) {}
            Text(stringResource(R.string.coming_soon))
*/
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
