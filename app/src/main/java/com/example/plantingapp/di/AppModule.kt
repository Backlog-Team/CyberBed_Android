package com.example.plantingapp.di

import android.util.Log
import android.webkit.CookieManager
import com.example.plantingapp.MainActivity
import com.example.plantingapp.data.remote.PlantApi
import com.example.plantingapp.data.repository.PlantRepository
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.management.NavbarManager
import com.example.plantingapp.management.PermissionsManager
import com.example.plantingapp.ui.screens.auth.AuthViewModel
import com.example.plantingapp.ui.screens.explore.ExploreViewModel
import com.example.plantingapp.ui.screens.explore.custom.create.CustomCreateViewModel
import com.example.plantingapp.ui.screens.explore.custom.plants.CustomViewModel
import com.example.plantingapp.ui.screens.explore.search.SearchViewModel
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel
import com.example.plantingapp.ui.screens.saved.SavedViewModel
import com.example.plantingapp.ui.screens.scan.ScanViewModel
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothViewModel
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<CookieJar> {
        object:  CookieJar {
            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookieManager = CookieManager.getInstance()
                val cookies: ArrayList<Cookie> = ArrayList()
                if (cookieManager.getCookie(url.toString()) != null) {
                    val splitCookies =
                        cookieManager.getCookie(url.toString()).split("[,;]".toRegex())
                            .dropLastWhile { it.isEmpty() }.toTypedArray()
                    for (i in splitCookies.indices) {
                        cookies.add(Cookie.parse(url, splitCookies[i].trim { it <= ' ' })!!)
                        Log.e(
                            "kilo",
                            "loadForRequest :Cookie.add ::  " + Cookie.parse(
                                url,
                                splitCookies[i].trim { it <= ' ' })!!
                        )
                    }
                }
                return cookies
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                val cookieManager = CookieManager.getInstance()
                for (cookie in cookies) {
                    cookieManager.setCookie(url.toString(), cookie.toString())
                    Log.e(
                        "kilo",
                        "saveFromResponse :  Cookie url : " + url.toString() + cookie.toString()
                    )
                }
            }
        }
    }
    single<PlantRepositoryInterface> {
        val baseUrl = "https://zenehu.space/"

        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .cookieJar(get())
            .build()

        val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(PlantApi::class.java)

        PlantRepository(api)
    }
    single { NavbarManager() }

    scope<MainActivity> {
        scoped { PermissionsManager(get()) }
    }

    viewModel { FoldersViewModel(get()) }
    viewModel { ScanViewModel(get()) }
    viewModel { BluetoothViewModel() }
    viewModel { AuthViewModel(get()) }
    viewModel { ExploreViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SavedViewModel(get()) }
    viewModel { CustomViewModel(get()) }
    viewModel { CustomCreateViewModel(get()) }

}