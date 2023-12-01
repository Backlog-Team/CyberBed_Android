package com.example.plantingapp.ui.screens.explore.custom.plants

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.utils.Resource
import com.example.plantingapp.domain.models.CustomPlant
import com.example.plantingapp.domain.usecases.CustomUseCase
import com.example.plantingapp.ui.states.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomViewModel(
    private val repository: PlantRepositoryInterface
): ViewModel() {
    private val useCase = CustomUseCase(repository)

    private val _loadingStates = MutableStateFlow(LoadingStates.NotLoading)
    val loadingState = _loadingStates.asStateFlow()
    private val _customPlants = MutableStateFlow(emptyList<CustomPlant>())
    val customPlants: StateFlow<List<CustomPlant>> = _customPlants
    val refreshing = mutableStateOf(false)

    fun loadCustomPlants() {
        viewModelScope.launch {
            useCase.getCustomPlants()
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            _loadingStates.value = LoadingStates.Error
                            it.message
                        }

                        is Resource.Loading -> _loadingStates.value = LoadingStates.Loading

                        is Resource.Success -> {
                            _customPlants.value = it.data ?: listOf()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    init {
        loadCustomPlants()
    }
}