package com.example.plantingapp.domain.usecases

import android.util.Log
import com.example.plantingapp.authCookie
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserCreated
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AuthUseCase(
    private val repository: PlantRepositoryInterface
) {
    fun auth(): Flow<Resource<UserCreated>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.auth()

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
            } else {
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun login(user: User): Flow<Resource<UserCreated>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.login(user)

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
                authCookie = process.headers()["set-cookie"]
                Log.d("kilo", "Raw: ${process.raw()}")
                Log.d("kilo", "Header: ${process.headers()["set-cookie"]}")
            } else {
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun signup(user: User): Flow<Resource<UserCreated>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.signup(user)

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
            } else {
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun logout(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.logout()

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
            } else {
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }
}