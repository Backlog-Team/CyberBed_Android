package com.example.plantingapp.ui.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserCreated
import com.example.plantingapp.domain.usecases.AuthUseCase
import com.example.plantingapp.domain.usecases.Resource
import com.example.plantingapp.ui.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    repository: PlantRepositoryInterface
): ViewModel() {
    //val cookie:

    private val useCase = AuthUseCase(repository)
    private val _loadingStates = MutableStateFlow(LoadingStates.Loading)
    val loadingState = _loadingStates.asStateFlow()

    private val _userId = MutableStateFlow(UserCreated())
    val userId: StateFlow<UserCreated> = _userId

    fun login(username: String, password: String) {
        val user = User(username, password)
        viewModelScope.launch {
            useCase.login(user)
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            Log.d("kilo", "Internet error")
                            _loadingStates.value = LoadingStates.Error
                        }

                        is Resource.Loading -> {
                            Log.d("kilo", "Loading...")
                            LoadingStates.Loading
                        }

                        is Resource.Success -> {
                            Log.d("kilo", "Success: $it")
                            _userId.value = it.data ?: UserCreated()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> {
                            Log.d("kilo", "Some error")
                            _loadingStates.value = LoadingStates.Error
                        }
                    }
                }
        }
    }
}