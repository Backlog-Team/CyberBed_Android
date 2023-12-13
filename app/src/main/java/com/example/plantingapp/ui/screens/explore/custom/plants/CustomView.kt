package com.example.plantingapp.ui.screens.explore.custom.plants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.components.cards.CustomPlantCard
import com.example.plantingapp.ui.components.TabHeader
import com.example.plantingapp.ui.components.containers.ItemsList
import com.example.plantingapp.ui.components.containers.TabView
import com.example.plantingapp.ui.screens.explore.custom.create.CustomCreateScreen
import com.example.plantingapp.ui.screens.explore.custom.create.CustomCreateViewModel
import com.example.plantingapp.ui.states.LoadingStates
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomView(
    viewModel: CustomViewModel
) {
    val loadingState = viewModel.loadingState.collectAsState()
    val customPlants by viewModel.customPlants.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    val createViewModel: CustomCreateViewModel = getViewModel()

    val refreshing = viewModel.refreshing.value
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.loadCustomPlants() })
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCustomPlants()
    }
    TabView {
        Column {
            TabHeader("Созданные растения")
            Button(
                onClick = {
                    navigator.push(CustomCreateScreen(createViewModel))
                }
            ) {
                Text("Создать растение")
            }
            when (loadingState.value) {
                LoadingStates.Success -> {
                    if (customPlants.isEmpty()) {
                        Text("Результатов не найдено")
                    } else {
                        ItemsList({viewModel.loadCustomPlants()}) {
                            items(customPlants.size) { index ->
                                CustomPlantCard(
                                    customPlant = customPlants[index],
                                    getViewModel()
                                )
                            }
                        }
                    }
                }

                LoadingStates.Error -> {
                    Text("Ошибка загрузки")
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
                    Text("Загрузка еще не началась...")
                }
            }
        }
    }
}