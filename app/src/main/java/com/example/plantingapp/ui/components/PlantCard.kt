package com.example.plantingapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.plantingapp.R
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.explore.ExploreViewModel
import com.example.plantingapp.ui.screens.shared.details.PlantDetailsScreen
import com.example.plantingapp.ui.theme.GreenBackground
import com.example.plantingapp.ui.theme.GreenPrimary
import org.koin.androidx.compose.getViewModel

@Composable
fun PlantCard(plant: Plant) {
    val navigator = LocalNavigator.current
    val viewModel = getViewModel<ExploreViewModel>()
    val context = LocalContext.current
    val imageUrl = "https://zenehu.space/api/search/plants/${plant.id}/image"
    val imageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .build()
    val placeholderImage = R.drawable.img_placeholder
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .placeholder(placeholderImage)
        .error(placeholderImage)
        .fallback(placeholderImage)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
    imageLoader.enqueue(imageRequest)
    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .width(300.dp)
            .clickable {
                navigator?.push(PlantDetailsScreen(plant, viewModel, imageRequest))
            },
        shape = RoundedCornerShape(5.dp),
        colors =  CardDefaults.cardColors(
            containerColor = GreenBackground
        ),
        border = BorderStroke(1.dp, GreenPrimary)
    ) {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .width(180.dp)
            ) {
                Text(
                    text = plant.displayPid ?: "-",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
