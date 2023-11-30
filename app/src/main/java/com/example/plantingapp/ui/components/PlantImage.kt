package com.example.plantingapp.ui.components

import android.util.Log
import android.webkit.CookieManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.plantingapp.R

@Composable
fun PlantImage(
    modifier: Modifier,
    imageUrl: String
) {
    val cookieManager = CookieManager.getInstance()
    val cookie = cookieManager.getCookie(imageUrl)

    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .build()
    val placeholderImage = R.drawable.img_placeholder
    val imageRequest = ImageRequest.Builder(context)
        .setHeader("Cookie", cookie)
        .data(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .placeholder(placeholderImage)
        .error(placeholderImage)
        .fallback(placeholderImage)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
    imageLoader.enqueue(imageRequest)

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        onError = { Log.e("kilo", "Error when loading image: "+ it.result.throwable) },
        onLoading = { Log.e("kilo", "Loading image: $imageUrl") },
        onSuccess = { Log.e("kilo", "Loaded image successfully: $imageUrl") },
        )
}