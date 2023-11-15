package com.example.plantingapp.data.remote

import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserCreated
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface PlantApi {
    // Authorization
    @GET("/api/auth")
    suspend fun auth(): Response<UserCreated>

    @POST("/api/signup")
    suspend fun signup(
        @Body user: User
    ): Response<UserCreated>

    @POST("/api/login")
    suspend fun login(
        @Body user: User
    ): Response<UserCreated>

    @DELETE("/api/logout")
    suspend fun logout(
        @Body user: User
    ): Response<Unit>


    // From library
    @Multipart
    @POST("/api/recognize")
    suspend fun recognize(
        @Part plantImage: MultipartBody.Part
    ): Response<List<Plant>>

    @GET("api/search/plants/{plantID}")
    suspend fun searchPlantById(
        @Path("plantID") plantID: Int
    ): Response<Plant>

    @GET("api/search/plants")
    suspend fun searchPlantByName(
        @Query("name") plantName: String?
    ): Response<List<Plant>>

    @GET("api/search/plants/{plantID}/image")
    suspend fun searchPlantImage(
        @Path("plantID") plantID: Int
    ): Response<ResponseBody>

    @GET("api/search/plants")
    suspend fun searchPlants(
        @Query("page") page: Int
    ): Response<List<Plant>>

    // User's plants
    @POST("api/plants/{plantID}")
    suspend fun addPlant(
        @Path("plantID") plantID: Int
    ): Response<Unit>

    @GET("api/plants")
    suspend fun getPlants(): Response<List<Plant>>

    @GET("api/plants/{plantID}")
    suspend fun getPlant(
        @Path("plantID") plantID: Int
    ): Response<Plant>

    @DELETE("api/plants/{plantID}")
    suspend fun deletePlant(
        @Path("plantID") plantID: Int
    ): Response<Unit>
}

