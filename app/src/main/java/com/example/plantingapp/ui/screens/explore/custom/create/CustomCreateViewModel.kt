package com.example.plantingapp.ui.screens.explore.custom.create

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.Resource
import com.example.plantingapp.domain.models.CustomPlant
import com.example.plantingapp.domain.usecases.CustomUseCase
import com.example.plantingapp.ui.states.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomCreateViewModel(
    private val repository: PlantRepositoryInterface
): ViewModel() {
    private val useCase = CustomUseCase(repository)

    private val _loadingStates = MutableStateFlow(LoadingStates.NotLoading)
    val loadingState = _loadingStates.asStateFlow()
    private val _created = MutableStateFlow(CustomPlant())

    fun createCustomPlant(name: String, descr: String, image: Bitmap?) {
        viewModelScope.launch {
            useCase.createCustomPlant(
                plantName = name,
                about = descr,
                image = image
            )
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            _loadingStates.value = LoadingStates.Error
                            it.message
                        }

                        is Resource.Loading -> _loadingStates.value = LoadingStates.Loading

                        is Resource.Success -> {
                            _created.value = it.data ?: CustomPlant()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    fun changeCustomPlant(name: String, descr: String, image: Bitmap) {
        viewModelScope.launch {
            useCase.createCustomPlant(
                plantName = name,
                about = descr,
                image = image
            )
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            _loadingStates.value = LoadingStates.Error
                            it.message
                        }

                        is Resource.Loading -> _loadingStates.value = LoadingStates.Loading

                        is Resource.Success -> {
                            _created.value = it.data ?: CustomPlant()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }
}