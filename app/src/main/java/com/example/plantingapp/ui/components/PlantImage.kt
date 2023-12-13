package com.example.plantingapp.ui.components

import android.util.Log
import android.webkit.CookieManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.plantingapp.R
import com.example.plantingapp.utils.Constants

@Composable
fun PlantImage(
    modifier: Modifier,
    imageUrl: String,
    cashed: Boolean = true
) {
    val cookieManager = CookieManager.getInstance()
    val cookie = cookieManager.getCookie(imageUrl) ?:""

    val context = LocalContext.current
    val placeholderImage = R.drawable.img_placeholder
    val imageRequest = ImageRequest.Builder(context)
        .setHeader(stringResource(R.string.cookie), cookie)
        .data(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .placeholder(placeholderImage)
        .error(placeholderImage)
        .fallback(placeholderImage)
        .memoryCachePolicy(if (cashed) CachePolicy.ENABLED else CachePolicy.DISABLED)
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        onError = { Log.e(Constants.DEBUG_TAG, "Error when loading image: "+ it.result.throwable) },
        onLoading = { Log.e(Constants.DEBUG_TAG, "Loading image: $imageUrl") },
        onSuccess = { Log.e(Constants.DEBUG_TAG, "Loaded image successfully: $imageUrl") },
    )
}