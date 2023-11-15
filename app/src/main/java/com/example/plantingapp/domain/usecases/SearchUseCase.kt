package com.example.plantingapp.domain.usecases

import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class SearchUseCase(
    private val repository: PlantRepositoryInterface
) {
    fun searchByName(name: String?): Flow<Resource<List<Plant>>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.searchPlantByName(name)

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