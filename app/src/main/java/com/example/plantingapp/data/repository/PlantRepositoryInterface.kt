package com.example.plantingapp.data.repository

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.example.plantingapp.domain.models.CustomPlant
import com.example.plantingapp.domain.models.Folder
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

    suspend fun searchPlantByName(plantName: String): Response<List<Plant>>

    suspend fun searchPlantImage(plantID: Int): Response<ResponseBody>

    suspend fun loadPlants(): Flow<PagingData<Plant>>

    suspend fun addPlant(plantID: Int): Response<Unit>

    suspend fun getPlants(): Response<List<Plant>>

    suspend fun getPlant(plantID: Int): Response<Plant>

    suspend fun deletePlant(plantID: Int): Response<Unit>

    suspend fun savePlant(plantID: Int): Response<Unit>

    suspend fun getSavedPlants(): Response<List<Plant>>

    suspend fun delSavedPlant(plantID: Int): Response<Unit>

    //Custom
    suspend fun createCustomPlant(
        plantName: String,
        about: String,
        image: Bitmap,
    ): Response<CustomPlant>


    suspend fun getCustomPlants(): Response<List<CustomPlant>>

    suspend fun getCustomPlant(plantID: Int): Response<CustomPlant>

    suspend fun changeCustomPlant(
        plantID: Int,
        plantName: String,
        about: String,
        image: Bitmap,
    ): Response<Unit>

    //Folders
    suspend fun createFolder(
        folderName: String
    ): Response<UserCreated>


    suspend fun getFolders(): Response<List<Folder>>

    suspend fun addPlantToFolder(
        folderID: Int,
        plantID: Int
    ): Response<Unit>

    suspend fun delPlantFromFolder(
        folderID: Int,
        plantID: Int
    ): Response<Unit>

    suspend fun getPlantsFromFolder(
        folderID: Int,
    ): Response<List<Plant>>

    suspend fun delFolder(
        folderID: Int,
    ): Response<Unit>
}