package com.example.plantingapp.ui.screens.explore.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantingapp.ui.LoadingStates
import com.example.plantingapp.ui.components.PlantCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    viewModel: SearchViewModel
) {
    val loadingState = viewModel.loadingState.collectAsState()
    val plants by viewModel.searchResults.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchText,
        onQueryChange = {
            searchText = it
            viewModel.loadSearchResults(searchText)

        },
        onSearch = {
            searchActive = false
            viewModel.loadSearchResults(searchText)
        },
        active = searchActive,
        onActiveChange = {
            searchActive = it
        },
        placeholder = {
            Text(text = "Enter your query")
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        }) {
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
