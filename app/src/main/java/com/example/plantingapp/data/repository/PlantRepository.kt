package com.example.plantingapp.data.repository

import android.graphics.Bitmap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.plantingapp.data.paging.PlantPagingSource
import com.example.plantingapp.data.remote.PlantApi
import com.example.plantingapp.domain.models.CustomPlant
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserId
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import kotlin.random.Random
import kotlin.random.nextUInt

class PlantRepository(
    private val plantApi: PlantApi
) : PlantRepositoryInterface {
    override suspend fun auth(): Response<UserId> {
        return plantApi.auth()
    }

    override suspend fun signup(user: User): Response<UserId> {
        return plantApi.signup(user)
    }

    override suspend fun login(user: User): Response<UserId> {
        return plantApi.login(user)
    }

    override suspend fun logout(): Response<Unit> {
        return plantApi.logout()
    }

    override suspend fun recognizeImage(image: Bitmap): Response<List<Plant>> {
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val imagePart = MultipartBody.Part.createFormData(
            "images",
            Random.nextUInt(8000000u).toString().plus(".jpg"),
            byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
        )
        return plantApi.recognize(imagePart)
    }

    override suspend fun searchPlantById(plantID: Int): Response<Plant> {
        return plantApi.searchPlantById(plantID)
    }

    override suspend fun searchPlantByName(plantName: String): Response<List<Plant>> {
        return plantApi.searchPlantByName(plantName)
    }

    override suspend fun searchPlantImage(plantID: Int): Response<ResponseBody> {
        return plantApi.searchPlantImage(plantID)
    }

    override suspend fun loadPlants(): Flow<PagingData<Plant>> {
        return Pager(
            PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { PlantPagingSource(plantApi) }
        ).flow
    }

    override suspend fun addPlant(plantID: Int): Response<Unit> {
        return plantApi.addPlant(plantID)
    }

    override suspend fun getPlants(): Response<List<Plant>> {
        return plantApi.getPlants()
    }

    override suspend fun getPlant(plantID: Int): Response<Plant> {
        return plantApi.getPlant(plantID)
    }

    override suspend fun deletePlant(plantID: Int): Response<Unit> {
        return plantApi.deletePlant(plantID)
    }

    override suspend fun savePlant(plantID: Int): Response<Unit> {
        return plantApi.savePlant(plantID)
    }

    override suspend fun getSavedPlants(): Response<List<Plant>> {
        return plantApi.getSavedPlants()
    }

    override suspend fun delSavedPlant(plantID: Int): Response<Unit> {
        return plantApi.delSavedPlant(plantID)
    }

    //Custom
    override suspend fun createCustomPlant(
        plantName: String?,
        about: String?,
        image: Bitmap?,
    ): Response<CustomPlant> {
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArray = stream.toByteArray()
        val imagePart = MultipartBody.Part.createFormData(
            "image",
            Random.nextUInt(8000000u).toString().plus(".jpg"),
            byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
        )

        val plantPart = plantName?.let { MultipartBody.Part.createFormData("plantName", it) }
        val descPart = about?.let { MultipartBody.Part.createFormData("about", it) }

        return plantApi.createCustomPlant(plantPart, descPart, imagePart)
    }


    override suspend fun getCustomPlants(): Response<List<CustomPlant>> {
        return plantApi.getCustomPlants()
    }

    override suspend fun getCustomPlant(plantID: Int): Response<CustomPlant>{
        return plantApi.getCustomPlant(plantID)
    }

    override suspend fun changeCustomPlant(
        plantID: Int,
        plantName: String,
        about: String,
        image: Bitmap?,
    ): Response<Unit> {
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val imagePart = MultipartBody.Part.createFormData(
            "image",
            Random.nextUInt(8000000u).toString().plus(".jpg"),
            byteArray.toRequestBody("image/*".toMediaTypeOrNull(), 0, byteArray.size)
        )

        val plantPart = MultipartBody.Part.createFormData("plantName", plantName)
        val descPart = MultipartBody.Part.createFormData("about", about)

        return plantApi.changeCustomPlant(plantID, plantPart, descPart, imagePart)
    }

    override suspend fun delCustomPlant(plantID: Int): Response<Unit> {
        return plantApi.delCustomPlant(plantID)
    }

    //Folders
    override suspend fun createFolder(folderName: String): Response<UserId> {
        return plantApi.createFolder(folderName)
    }

    override suspend fun getFolders(): Response<List<Folder>> {
        return plantApi.getFolders()
    }

    override suspend fun addPlantToFolder(
        folderID: Int,
        plantID: Int,
        wateringInterval: String
    ): Response<Unit> {
        return plantApi.addPlantToFolder(folderID, plantID, wateringInterval)
    }

    override suspend fun delPlantFromFolder(folderID: Int, plantID: Int): Response<Unit> {
        return plantApi.delPlantFromFolder(folderID, plantID)
    }

    override suspend fun getPlantsFromFolder(folderID: Int): Response<List<Plant>> {
        return plantApi.getPlantsFromFolder(folderID)
    }

    override suspend fun delFolder(folderID: Int): Response<Unit> {
        return plantApi.delFolder(folderID)
    }
}