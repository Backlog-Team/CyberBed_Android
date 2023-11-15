package com.example.plantingapp.ui.screens.explore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.usecases.Resource
import com.example.plantingapp.domain.usecases.SearchUseCase
import com.example.plantingapp.ui.LoadingStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val repository: PlantRepositoryInterface
): ViewModel() {
    private val _pagingData: MutableState<Flow<PagingData<Plant>>> = mutableStateOf(emptyFlow())
    val pagingData: State<Flow<PagingData<Plant>>> = _pagingData

    private val _loadingStates = MutableStateFlow(LoadingStates.NotLoading)
    val loadingState = _loadingStates.asStateFlow()
    private val _plants = MutableStateFlow(emptyList<Plant>())
    val plants: StateFlow<List<Plant>> = _plants

    init {
        viewModelScope.launch {
            val response = repository.loadPlants()
            _pagingData.value = response.cachedIn(viewModelScope)
        }
    }

    val useCase = SearchUseCase(repository)
    fun loadSearchResults(name: String) {
        viewModelScope.launch {
            useCase.searchByName(name)
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value =
                            LoadingStates.Error

                        is Resource.Loading -> LoadingStates.Loading

                        is Resource.Success -> {
                            _plants.value = it.data ?: listOf()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value =
                            LoadingStates.Error
                    }

                }
        }
    }
}