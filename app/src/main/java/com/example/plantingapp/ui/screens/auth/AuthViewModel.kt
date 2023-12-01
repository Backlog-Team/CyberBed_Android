package com.example.plantingapp.ui.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.utils.Resource
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserId
import com.example.plantingapp.domain.usecases.AuthUseCase
import com.example.plantingapp.ui.states.LoadingStates
import com.example.plantingapp.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    repository: PlantRepositoryInterface
): ViewModel() {
    private val useCase = AuthUseCase(repository)
    private val _loadingStates = MutableStateFlow(LoadingStates.NotLoading)
    val loadingState = _loadingStates.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _userId = MutableStateFlow(UserId())

    private val _authState = MutableStateFlow(AuthStates.LoggedOut)
    val authState = _authState.asStateFlow()

    fun login(username: String, password: String) {
        val user = User(username, password)
        viewModelScope.launch {
            useCase.login(user)
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            _loadingStates.value = LoadingStates.Error
                        }

                        is Resource.Loading -> {
                            _loadingStates.value = LoadingStates.Loading
                            _authState.value = AuthStates.Logging
                        }

                        is Resource.Success -> {
                            _userId.value = it.data ?: UserId()
                            _authState.value = AuthStates.LoggedIn
                        }

                        else -> {
                            Log.d(Constants.DEBUG_TAG,"Some error: ${it.message}, data: ${it.data}")
                            _loadingStates.value = LoadingStates.Error
                            _message.value = "Ошибка авторизации! Попробуйте еще раз!"
                        }
                    }
                }
        }
    }
    fun signup(username: String, password: String) {
        val user = User(username, password)
        viewModelScope.launch {
            useCase.signup(user)
                .collect {
                    when (it) {
                        is Resource.Internet -> {
                            Log.d(Constants.DEBUG_TAG, "error: ${it.message}, data: ${it.data}")
                            _loadingStates.value = LoadingStates.Error
                        }

                        is Resource.Loading -> {
                            Log.d(Constants.DEBUG_TAG, "Loading...")
                            _loadingStates.value = LoadingStates.Loading
                            _authState.value = AuthStates.Logging

                        }

                        is Resource.Success -> {
                            Log.d(Constants.DEBUG_TAG, "Success: $it")
                            _userId.value = it.data ?: UserId()
                            _loadingStates.value = LoadingStates.Success
                            _authState.value = AuthStates.LoggedIn
                        }

                        else -> {
                            Log.d(Constants.DEBUG_TAG,"Some error: ${it.message}, data: ${it.data}")
                            _loadingStates.value = LoadingStates.Error
                        }
                    }
                }
        }
    }

    fun auth() {
        viewModelScope.launch {
            useCase.auth()
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _userId.value = it.data ?: UserId()
                            _authState.value = AuthStates.LoggedIn
                        }

                        is Resource.Loading -> {
                            _userId.value = it.data ?: UserId()
                            _authState.value = AuthStates.Logging
                        }

                        else -> {
                            Log.d(Constants.DEBUG_TAG,"Some error: ${it.message}, data: ${it.data}")
                            _authState.value = AuthStates.LoggedOut
                        }
                    }
                }
        }
    }
    fun logout() {
        viewModelScope.launch {
            useCase.logout()
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _authState.value = AuthStates.LoggedOut
                            _loadingStates.value = LoadingStates.NotLoading
                        }

                        is Resource.Loading -> {
                            _loadingStates.value = LoadingStates.Loading
                        }

                        else -> {
                            Log.d(Constants.DEBUG_TAG,"Some error: ${it.message}, data: ${it.data}")
                            _loadingStates.value = LoadingStates.Error
                        }
                    }
                }
        }
    }
}