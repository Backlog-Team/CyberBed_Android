package com.example.plantingapp.ui.components

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import com.example.plantingapp.ui.screens.saved.SavedViewModel
import com.example.plantingapp.ui.screens.shared.details.PlantDetailsScreen
import com.example.plantingapp.ui.theme.GreenBackground
import com.example.plantingapp.ui.theme.GreenPrimary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun PlantCard(plant: Plant, isSaved: MutableState<Boolean> = mutableStateOf(false)) {
    val navigator = LocalNavigator.current
    val exploreViewModel = getViewModel<ExploreViewModel>()
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
    val savedViewModel: SavedViewModel = getViewModel()

    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .width(300.dp)
            .clickable {
                navigator?.push(PlantDetailsScreen(plant, exploreViewModel, imageRequest))
            },
        shape = RoundedCornerShape(5.dp),
        colors =  CardDefaults.cardColors(
            containerColor = GreenBackground
        ),
        border = BorderStroke(1.dp, GreenPrimary)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.Top),
                    onClick = {
                        scope.launch {
                            savedViewModel.toastMessage.collect {
                                Toast.makeText(context, it, LENGTH_SHORT).show()
                            }
                        }
                        isSaved.value = !isSaved.value
                        if (isSaved.value) {
                            savedViewModel.savePlant(plant)

                        } else {
                            savedViewModel.delPlant(plant)
                        }
                    },
                    content = {
                        Icon(
                            painter = rememberVectorPainter
                                (image = if (isSaved.value)
                                    Icons.Default.Bookmark else Icons.Default.BookmarkBorder),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )

                    }
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(140.dp)
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

            Box(modifier = Modifier.align(Alignment.BottomStart)) {
                val showDialog =  remember { mutableStateOf(false) }

                IconButton(onClick = { showDialog.value = !showDialog.value }) {
                    Icon(painter = rememberVectorPainter(image = Icons.Default.Add),
                        contentDescription = null)
                }

                if(showDialog.value)
                    FoldersMenu(plant = plant, setShowDialog = {
                        showDialog.value = it
                    })
            }
        }
    }
}
