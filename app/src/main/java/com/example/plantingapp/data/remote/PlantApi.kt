package com.example.plantingapp.data.remote

import com.example.plantingapp.domain.models.Channel
import com.example.plantingapp.domain.models.CustomPlant
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.models.User
import com.example.plantingapp.domain.models.UserId
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface PlantApi {
    // Authorization
    @GET("/api/auth")
    suspend fun auth(): Response<UserId>

    @Headers("Access-Control-Allow-Credentials: true")
    @POST("/api/signup")
    suspend fun signup(
        @Body user: User
    ): Response<UserId>


    @Headers("Access-Control-Allow-Credentials: true")
    @POST("/api/login")
    suspend fun login(
        @Body user: User
    ): Response<UserId>

    @DELETE("/api/logout")
    suspend fun logout(): Response<Unit>


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
        @Query("name") plantName: String
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
        @Path("plantID") plantID: Int,
        @Query("wateringTime") wateringInterval: String?
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

    //Saved
    @POST("api/plants/{plantID}/saved")
    suspend fun savePlant(
        @Path("plantID") plantID: Int
    ): Response<Unit>

    @GET("api/plants/saved")
    suspend fun getSavedPlants(): Response<List<Plant>>

    @DELETE("api/plants/{plantID}/saved")
    suspend fun delSavedPlant(
        @Path("plantID") plantID: Int
    ): Response<Unit>

    //Custom
    @Multipart
    @POST("api/custom/plants")
    suspend fun createCustomPlant(
        @Part plantName: MultipartBody.Part?,
        @Part about: MultipartBody.Part?,
        @Part image: MultipartBody.Part?,
    ): Response<CustomPlant>

    @GET("api/custom/plants")
    suspend fun getCustomPlants(): Response<List<CustomPlant>>

    @GET("api/custom/plants/{plantID}")
    suspend fun getCustomPlant(
        @Path("plantID") plantID: Int
    ): Response<CustomPlant>

    @Multipart
    @PUT("api/custom/plants/{plantID}")
    suspend fun changeCustomPlant(
        @Path("plantID") plantID: Int,

        @Part plantName: MultipartBody.Part?,
        @Part about: MultipartBody.Part?,
        @Part image: MultipartBody.Part?,
    ): Response<Unit>

    @DELETE("api/custom/plants/{plantID}")
    suspend fun delCustomPlant(
        @Path("plantID") plantID: Int
    ): Response<Unit>

    //Folders
    @POST("api/folders")
    suspend fun createFolder(
        @Query("name") folderName: String
    ): Response<UserId>

    @GET("api/folders")
    suspend fun getFolders(): Response<List<Folder>>

    @POST("api/folders/{folderID}/plants/{plantID}")
    suspend fun addPlantToFolder(
        @Path("folderID") folderID: Int,
        @Path("plantID") plantID: Int,
        @Query("wateringTime") wateringInterval: String
    ): Response<Unit>

    @DELETE("api/folders/{folderID}/plants/{plantID}")
    suspend fun delPlantFromFolder(
        @Path("folderID") folderID: Int,
        @Path("plantID") plantID: Int
    ): Response<Unit>

    @GET("api/folders/{folderID}/plants")
    suspend fun getPlantsFromFolder(
        @Path("folderID") folderID: Int,
    ): Response<List<Plant>>

    @DELETE("api/folders/{folderID}")
    suspend fun delFolder(
        @Path("folderID") folderID: Int,
    ): Response<Unit>

    @POST("api/folders/{folderID}/default/plants/{plantID}")
    suspend fun saveToDefaultFolder(
        @Path("folderID") folderID: Int,
        @Path("plantID") plantID: Int,
    ): Response<Unit>

    @POST("api/folders/{fromFolderID}/{toFolderID}/plants/{plantID}")
    suspend fun changeFolder(
        @Path("fromFolderID") fromFolderID: Int,
        @Path("toFolderID") toFolderID: Int,
        @Path("plantID") plantID: Int,
    ): Response<Unit>

    @POST("api/plants/{plantID}/chan/{channelID}")
    suspend fun setChannel(
        @Path("plantID") plantID: Int,
        @Path("channelID") channelID: Int,
    ): Response<Unit>

    @POST("api/plants/{plantID}/chan")
    suspend fun getChannel(
        @Path("plantID") plantID: Int,
    ): Response<Channel>
}

