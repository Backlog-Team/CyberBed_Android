package com.example.plantingapp.domain.usecases

import android.util.Log
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.Resource
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class AuthUseCase(
    private val repository: PlantRepositoryInterface
) {
    fun auth(): Flow<Resource<UserId>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.auth()

            if (process.isSuccessful) {
                Log.d("kilo", process.raw().toString())
                emit(Resource.Success(process.body()))
            } else {
                Log.d("kilo", process.raw().toString())
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun login(user: User): Flow<Resource<UserId>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.login(user)

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

    fun signup(user: User): Flow<Resource<UserId>> = flow {
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