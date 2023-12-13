package com.example.plantingapp.domain.usecases

import android.util.Log
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.Channel
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.utils.Constants
import com.example.plantingapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ChannelsUseCase(
    private val repository: PlantRepositoryInterface
) {
    fun setChannel(
        plant: Plant,
        channel: Channel
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.setChannel(plant.id, channel.channelID ?: 0)

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

    fun getChannel(
        plant: Plant
    ): Flow<Resource<Channel>> = flow {
        try {
            emit(Resource.Loading())

            val process = repository.getChannel(plant.id)

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
}