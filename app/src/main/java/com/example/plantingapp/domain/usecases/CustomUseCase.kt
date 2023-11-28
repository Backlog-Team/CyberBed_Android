package com.example.plantingapp.domain.usecases

import android.graphics.Bitmap
import android.util.Log
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.Resource
import com.example.plantingapp.domain.models.CustomPlant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CustomUseCase(
    private val repository: PlantRepositoryInterface
) {
    fun createCustomPlant(
        plantName: String,
        about: String,
        image: Bitmap?,
    ): Flow<Resource<CustomPlant>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.createCustomPlant(plantName, about, image)
            Log.d("kilo", process.raw().toString())

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

    fun getCustomPlants(): Flow<Resource<List<CustomPlant>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getCustomPlants()
            Log.d("kilo", process.raw().toString())

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

    fun getCustomPlant(plantId: Int): Flow<Resource<CustomPlant>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getCustomPlant(plantId)
            Log.d("kilo", process.raw().toString())

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

    fun changeCustomPlant(
        plantID: Int,
        plantName: String,
        about: String,
        image: Bitmap,
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.changeCustomPlant(plantID, plantName, about, image)
            Log.d("kilo", process.raw().toString())

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