package com.example.plantingapp.domain.usecases

import android.util.Log
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.Resource
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.models.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class FoldersUseCase(
    private val repository: PlantRepositoryInterface
) {
    fun createFolder(name: String): Flow<Resource<UserId>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.createFolder(name)

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
            } else {
                Log.e("kilo", process.raw().toString())

                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun getFolders(): Flow<Resource<List<Folder>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getFolders()

            if (process.isSuccessful) {
                emit(Resource.Success(process.body()))
            } else {
                Log.e("kilo", process.raw().toString())

                val errMsg = process.errorBody()?.string()
                emit(Resource.Error.GeneralError(errMsg!!))
            }
        } catch (e: IOException) {
            emit(Resource.Internet("No connection"))
        }
    }

    fun addPlantToFolder(folder: Folder, plant: Plant, wateringInterval: String): Flow<Resource<Unit>> = flow {
        try {

            emit(Resource.Loading())

            val process = repository.addPlantToFolder(folder.id ?: 0, plant.id, wateringInterval)

            if (process.isSuccessful) {
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

    fun delPlantFromFolder(folder: Folder, plant: Plant): Flow<Resource<Unit>> = flow {
        try {

            emit(Resource.Loading())

            val process = repository.delPlantFromFolder(folder.id ?: 0, plant.id)

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

    fun getPlantsFromFolder(folder: Folder): Flow<Resource<List<Plant>>> = flow {
        try {

            emit(Resource.Loading())

            val process = repository.getPlantsFromFolder(folder.id ?: 0)

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

    fun delFolder(folder: Folder): Flow<Resource<Unit>> = flow {
        try {

            emit(Resource.Loading())

            val process = repository.delFolder(folder.id ?: 0)

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