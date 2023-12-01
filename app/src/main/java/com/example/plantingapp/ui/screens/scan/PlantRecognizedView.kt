package com.example.plantingapp.ui.screens.scan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.R
import com.example.plantingapp.ui.states.LoadingStates
import com.example.plantingapp.ui.components.cards.PlantCard
import com.example.plantingapp.ui.components.containers.TabView

@Composable
fun PlantRecognizedView(
    viewModel: ScanViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    val loadingState = viewModel.loadingState.collectAsState()
    val plants by viewModel.plants.collectAsState()
    TabView {
        when (loadingState.value) {
            LoadingStates.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Text(
                        text = stringResource(R.string.scan_success),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = stringResource(R.string.possible_variants),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(plants.size) { index ->
                            PlantCard(plant = plants[index])
                        }
                    }
                    Button(onClick = ({
                        navigator.pop()
                    }
                            )) {
                        Text(stringResource(R.string.scan_again))
                    }
                }
            }

            LoadingStates.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.scan_failed))
                    Button(onClick = { navigator.push(CameraScreen(viewModel)) }) {
                        Text( stringResource(id = R.string.retry))
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