package com.example.plantingapp.ui.screens.camera

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.LoadingStates
import com.example.plantingapp.ui.components.PlantCard

@Composable
fun PlantRecognizedView(
    viewModel: CameraViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    val loadingState = viewModel.loadingState.collectAsState()
    val plants by viewModel.plants.collectAsState()
    when (loadingState.value) {
        LoadingStates.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                ) {
                Text(
                    text = "Растение распознано!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Возможные варианты:",
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
                    Text("Распознать еще раз")
                }
            }
        }

        LoadingStates.Error -> {
            Log.d("kilo", "-")
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Не удалось распознать растение")
                Button(onClick = { navigator.push(CameraScreen(viewModel)) }) {
                    Text("Попробовать еще раз")
                }
            }
        }
        LoadingStates.Loading -> {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        LoadingStates.NotLoading -> {
            Text("Поиск по фото еще не начался")
        }
    }
}