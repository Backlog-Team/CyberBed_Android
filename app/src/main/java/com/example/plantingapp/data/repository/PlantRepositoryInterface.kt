package com.example.plantingapp.data.repository

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserCreated
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

interface PlantRepositoryInterface {
    suspend fun auth(): Response<UserCreated>

    suspend fun signup(user: User): Response<UserCreated>

    suspend fun login(user: User): Response<UserCreated>

    suspend fun logout(): Response<Unit>

    suspend fun recognizeImage(image: Bitmap): Response<List<Plant>>

    suspend fun searchPlantById(plantID: Int): Response<Plant>

    suspend fun searchPlantByName(plantName: String?): Response<List<Plant>>

    suspend fun searchPlantImage(plantID: Int): Response<ResponseBody>

    suspend fun loadPlants(): Flow<PagingData<Plant>>

    suspend fun addPlant(plantID: Int): Response<Unit>

    suspend fun getPlants(): Response<List<Plant>>

    suspend fun getPlant(plantID: Int): Response<Plant>

    suspend fun deletePlant(plantID: Int): Response<Unit>
}