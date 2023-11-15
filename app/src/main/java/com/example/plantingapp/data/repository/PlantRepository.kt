package com.example.plantingapp.data.repository

import android.graphics.Bitmap
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.plantingapp.data.paging.PlantPagingSource
import com.example.plantingapp.data.remote.PlantApi
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserCreated
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
    override suspend fun auth(): Response<UserCreated> {
        TODO("Not yet implemented")
    }

    override suspend fun signup(user: User): Response<UserCreated> {
        TODO("Not yet implemented")
    }

    override suspend fun login(user: User): Response<UserCreated> {
        return plantApi.login(user)
    }

    override suspend fun logout(): Response<Unit> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun searchPlantByName(plantName: String?): Response<List<Plant>> {
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
        TODO("Not yet implemented")
    }

    override suspend fun getPlants(): Response<List<Plant>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlant(plantID: Int): Response<Plant> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlant(plantID: Int): Response<Unit> {
        TODO("Not yet implemented")
    }
}