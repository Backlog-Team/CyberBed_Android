package com.example.plantingapp.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.Channel
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.usecases.ChannelsUseCase
import com.example.plantingapp.utils.Resource
import com.example.plantingapp.domain.usecases.SavedUseCase
import com.example.plantingapp.ui.states.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedViewModel(
    repository: PlantRepositoryInterface
): ViewModel()  {
    private val savedUseCase = SavedUseCase(repository)
    private val channelsUseCase = ChannelsUseCase(repository)

    private val _loadingStates = MutableStateFlow(LoadingStates.NotLoading)
    val loadingStates = _loadingStates.asStateFlow()
    private val _plants = MutableStateFlow(emptyList<Plant>())
    val plants: StateFlow<List<Plant>> = _plants.asStateFlow()
    private val _channel = MutableStateFlow(Channel(0))
    val channel: StateFlow<Channel> = _channel.asStateFlow()
    private val _toastMessage = MutableStateFlow("")
    val toastMessage: StateFlow<String> = _toastMessage.asStateFlow()

    fun savePlant(plant: Plant, wateringTime: String?) {
        viewModelScope.launch {
            if (!plants.value.contains(plant)) {
                savedUseCase.savePlant(plant, wateringTime)
                    .collect {
                        when (it) {
                            is Resource.Loading -> _toastMessage.value = "Saving..."
                            is Resource.Success -> _toastMessage.value = "Saved successfully!"
                            else -> _toastMessage.value = "Error while saving :("
                        }
                    }
            } else {
                _toastMessage.value = "Already saved"
            }
        }
    }

    fun delPlant(plant: Plant) {
        if (plants.value.contains(plant)) {
            viewModelScope.launch {
                savedUseCase.delPlant(plant)
                    .collect {
                        when (it) {
                            is Resource.Loading -> _toastMessage.value = "Deleting..."
                            is Resource.Success -> _toastMessage.value = "Deleted successfully!"
                            else -> _toastMessage.value = "Error while deleting :("
                        }
                    }
            }
        } else {
            _toastMessage.value = "Already deleted"
        }
    }

    fun setChannel(
        plant: Plant,
        channel: Channel
    ) {
        viewModelScope.launch {
            channelsUseCase.setChannel(plant, channel)
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value = LoadingStates.Error
                        is Resource.Loading ->
                            _loadingStates.value = LoadingStates.Loading

                        is Resource.Success -> {
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    fun getChannel(
        plant: Plant
    ) {
        viewModelScope.launch {
            channelsUseCase.getChannel(plant)
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value = LoadingStates.Error
                        is Resource.Loading ->
                            _loadingStates.value = LoadingStates.Loading

                        is Resource.Success -> {
                            _loadingStates.value = LoadingStates.Success
                            _channel.value = it.data ?: Channel(0)
                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    fun getSavedPlants() {
        viewModelScope.launch {
            savedUseCase.getSavedPlants()
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value = LoadingStates.Error
                        is Resource.Loading ->
                            _loadingStates.value = LoadingStates.Loading
                        is Resource.Success -> {
                            _loadingStates.value = LoadingStates.Success
                            _plants.value = it.data ?: listOf()
                        }
                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }
    init {
        getSavedPlants()
    }
}