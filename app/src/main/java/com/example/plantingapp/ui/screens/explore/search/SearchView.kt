package com.example.plantingapp.ui.screens.explore.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.plantingapp.R
import com.example.plantingapp.ui.LoadingStates
import com.example.plantingapp.ui.components.PlantCard
import com.example.plantingapp.ui.screens.explore.ExploreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    viewModel: ExploreViewModel
) {
    val navigator = LocalNavigator.current
    val lazyListState = rememberLazyListState()
    val loadingState = viewModel.loadingState.collectAsState()
    val plants by viewModel.plants.collectAsState()
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Search",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchText,
                onValueChange = { searchText = it },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { viewModel.loadSearchResults(searchText) }
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color.Black
                    )
                }
            )
        }
        when (loadingState.value) {
            LoadingStates.Success -> {
                if (plants.isEmpty()) {
                    Text("Результатов не найдено")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(plants.size) { index ->
                            PlantCard(plant = plants[index])
                        }
                    }
                }
            }
            LoadingStates.Error -> {
                Text("Ошибка поиска")
            }
            LoadingStates.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            LoadingStates.NotLoading -> {
                Text("Попробуйте поиск растения по его названию!")
            }
        }
    }
}
