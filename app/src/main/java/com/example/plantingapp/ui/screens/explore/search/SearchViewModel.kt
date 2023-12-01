package com.example.plantingapp.ui.screens.explore.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.utils.Resource
import com.example.plantingapp.domain.usecases.SearchUseCase
import com.example.plantingapp.ui.states.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    repository: PlantRepositoryInterface
): ViewModel() {

    private val useCase = SearchUseCase(repository)
    private val _loadingStates = MutableStateFlow(LoadingStates.NotLoading)
    val loadingState = _loadingStates.asStateFlow()
    private val _searchResults = MutableStateFlow(emptyList<Plant>())
    val searchResults: StateFlow<List<Plant>> = _searchResults

    fun loadSearchResults(name: String) {
        viewModelScope.launch {
            useCase.searchByName(name)
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            _loadingStates.value = LoadingStates.Error
                            it.message
                        }

                        is Resource.Loading -> _loadingStates.value = LoadingStates.Loading

                        is Resource.Success -> {
                            _searchResults.value = it.data ?: listOf()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value =
                            LoadingStates.Error
                    }
                }
        }
    }
}