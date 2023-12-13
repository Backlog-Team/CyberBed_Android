package com.example.plantingapp.ui.screens.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.utils.Resource
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.usecases.FoldersUseCase
import com.example.plantingapp.ui.states.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoldersViewModel(
    repository: PlantRepositoryInterface
): ViewModel() {
    private val useCase = FoldersUseCase(repository)
    private var _folders = MutableStateFlow(emptyList<Folder>())
    var folders: StateFlow<List<Folder>> = _folders
    private var _plants = MutableStateFlow(emptyList<Plant>())
    var plants: StateFlow<List<Plant>> = _plants
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

    fun addPlantToFolder(folder: Folder, plant: Plant, wateringInterval: String) {
        viewModelScope.launch {
            useCase.addPlantToFolder(folder, plant, wateringInterval)
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

    fun getPlantFromFolder(folder: Folder) {
        viewModelScope.launch {
            useCase.getPlantsFromFolder(folder)
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value = LoadingStates.Error

                        is Resource.Success -> {
                            _plants.value = it.data ?: listOf()
                            _loadingStates.value = LoadingStates.Success
                        }

                        is Resource.Loading -> _loadingStates.value = LoadingStates.Loading

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    fun delPlantsFromFolder(folder: Folder, plant: Plant){
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
    }

    fun changeFolder(
        fromFolder: Folder,
        toFolder: Folder,
        plant: Plant
    ) {
        viewModelScope.launch {
            useCase.changeFolder(fromFolder, toFolder, plant)
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

    init {
        getFolders()
    }
}