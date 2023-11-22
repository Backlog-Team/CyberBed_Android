package com.example.plantingapp.ui.screens.home.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.Resource
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.usecases.FoldersUseCase
import com.example.plantingapp.ui.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoldersViewModel(
    private val repository: PlantRepositoryInterface
): ViewModel() {
    private val useCase = FoldersUseCase(repository)
    private var _folders = MutableStateFlow(emptyList<Folder>())
    var folders: StateFlow<List<Folder>> = _folders

    private val _loadingStates = MutableStateFlow(LoadingStates.Loading)
    val loadingState = _loadingStates.asStateFlow()

    private var _message = MutableStateFlow("")
    var message: StateFlow<String> = _message

    fun getFolders() {
        viewModelScope.launch {
            useCase.getFolders()
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value = LoadingStates.Error

                        is Resource.Loading -> _loadingStates.value = LoadingStates.Loading

                        is Resource.Success -> {
                            _folders.value = it.data ?: listOf()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    fun addPlantToFolder(folder: Folder, plant: Plant) {
        viewModelScope.launch {
            useCase.addPlantToFolder(folder, plant)
                .collect {
                    when (it) {
                        is Resource.Internet -> _message.value = "No internet connection"

                        is Resource.Loading -> _message.value = "Adding plant to folder..."

                        is Resource.Success -> _message.value = "Successfully added to folder"

                        else -> _message.value = "Error: ${it.message}"
                    }
                }
        }
    }

    fun delPlantFromFolder(folder: Folder, plant: Plant) {
        viewModelScope.launch {
            useCase.delPlantFromFolder(folder, plant)
                .collect {
                    when (it) {
                        is Resource.Internet -> _message.value = "No internet connection"

                        is Resource.Loading -> _message.value = "Deleting plant from folder..."

                        is Resource.Success -> _message.value = "Successfully deleted from folder"

                        else -> _message.value = "Error: ${it.message}"
                    }
                }
        }
    }

    fun delFolder(folder: Folder) {
        viewModelScope.launch {
            useCase.delFolder(folder)
                .collect {
                    when (it) {
                        is Resource.Internet -> _message.value = "No internet connection"

                        is Resource.Loading -> _message.value = "Deleting plant from folder..."

                        is Resource.Success -> _message.value = "Successfully deleted from folder"

                        else -> _message.value = "Error: ${it.message}"
                    }
                }
        }
        getFolders()
    }

    fun createFolder(folderName: String) {
        viewModelScope.launch {
            useCase.createFolder(folderName)
                .collect {
                    when (it) {
                        is Resource.Internet -> _message.value = "No internet connection"

                        is Resource.Loading -> _message.value = "Deleting plant from folder..."

                        is Resource.Success -> _message.value = "Successfully deleted from folder"

                        else -> _message.value = "Error: ${it.message}"
                    }
                }
        }
        getFolders()
    }
}