package com.example.plantingapp.di

import com.example.plantingapp.authCookie
import com.example.plantingapp.data.remote.CookieInterceptor
import com.example.plantingapp.data.remote.PlantApi
import com.example.plantingapp.data.repository.PlantRepository
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.ui.screens.auth.AuthViewModel
import com.example.plantingapp.ui.screens.camera.CameraViewModel
import com.example.plantingapp.ui.screens.explore.ExploreViewModel
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<PlantRepositoryInterface> {
        val baseUrl = "https://zenehu.space/"

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val cookieInterceptor = CookieInterceptor()
        cookieInterceptor.setSessionCookie(authCookie)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(cookieInterceptor)
            .addInterceptor(logger)
            .build()

        val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PlantApi::class.java)

        PlantRepository(api)
    }

    viewModel { CameraViewModel(get()) }
    viewModel { BluetoothViewModel() }
    viewModel { AuthViewModel(get()) }
    viewModel { ExploreViewModel(get()) }
}