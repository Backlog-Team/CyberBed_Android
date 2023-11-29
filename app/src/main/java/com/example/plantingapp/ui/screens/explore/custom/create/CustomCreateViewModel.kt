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
    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

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
                            _message.value = "Ошибка создания"

                            it.message
                        }

                        is Resource.Loading -> {
                            _loadingStates.value = LoadingStates.Loading
                            _message.value = "Создание..."
                        }

                        is Resource.Success -> {
                            _loadingStates.value = LoadingStates.Success
                            _message.value = "Создано успешно"

                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    fun changeCustomPlant(id: Int, name: String, descr: String, image: Bitmap?) {
        viewModelScope.launch {
            useCase.changeCustomPlant(
                plantID = id,
                plantName = name,
                about = descr,
                image = image
            )
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            _loadingStates.value = LoadingStates.Error
                            it.message
                            _message.value = "Ошибка изменения"

                        }

                        is Resource.Loading -> {
                            _loadingStates.value = LoadingStates.Loading
                            _message.value = "Изменение..."
                        }

                        is Resource.Success -> {
                            _loadingStates.value = LoadingStates.Success
                            _message.value = "Изменено успешно"

                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }


    fun delCustomPlant(id: Int) {
        viewModelScope.launch {
            useCase.delCustomPlant(
                plantID = id
            )
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            _loadingStates.value = LoadingStates.Error
                            _message.value = "Ошибка удаления"
                            it.message
                        }

                        is Resource.Loading -> {
                            _loadingStates.value = LoadingStates.Loading
                            _message.value = "Удаление..."

                        }

                        is Resource.Success -> {
                            _loadingStates.value = LoadingStates.Success
                            _message.value = "Удалено успешно"

                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }
}