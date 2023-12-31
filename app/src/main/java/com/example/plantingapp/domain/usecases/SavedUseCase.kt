package com.example.plantingapp.domain.usecases

import android.util.Log
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.utils.Resource
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SavedUseCase(
    private val repository: PlantRepositoryInterface
) {
    fun savePlant(plant: Plant, wateringTime: String?): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.addPlant(plant.id, wateringTime)

            if (process.isSuccessful) {
                emit(Resource.Success())
            } else {
                Log.d(Constants.DEBUG_TAG, process.raw().toString())

                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun delPlant(plant: Plant): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.deletePlant(plant.id)

            if (process.isSuccessful) {
                emit(Resource.Success())
            } else {
                Log.d(Constants.DEBUG_TAG, process.raw().toString())

                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun getSavedPlants(): Flow<Resource<List<Plant>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getPlants()

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
            } else {
                Log.d(Constants.DEBUG_TAG, process.raw().toString())
                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }
}