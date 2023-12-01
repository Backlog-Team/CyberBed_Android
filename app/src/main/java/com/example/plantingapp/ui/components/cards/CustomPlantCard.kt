package com.example.plantingapp.ui.components.cards

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.R
import com.example.plantingapp.domain.models.CustomPlant
import com.example.plantingapp.ui.components.PlantImage
import com.example.plantingapp.ui.screens.explore.custom.create.CustomCreateScreen
import com.example.plantingapp.ui.screens.explore.custom.create.CustomCreateViewModel
import com.example.plantingapp.ui.theme.GrayBackground
import com.example.plantingapp.utils.Constants
import kotlinx.coroutines.launch

@Composable
fun CustomPlantCard(
    customPlant: CustomPlant,
    viewModel: CustomCreateViewModel
) {
    Log.d(Constants.DEBUG_TAG, customPlant.toString())
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(300.dp)
            .height(200.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = GrayBackground
        ),
    ) {
        Box(Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(7f)
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = customPlant.plantName ?: stringResource(id = R.string.empty),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(modifier = Modifier.padding(5.dp),
                        text = customPlant.about ?: stringResource(id = R.string.empty)
                    )
                }
                PlantImage(
                    modifier = Modifier
                        .weight(5f)
                        .padding(vertical = 15.dp, horizontal = 5.dp)
                        .aspectRatio(ratio = 1f)
                        .clip(RoundedCornerShape(5.dp)),
                    imageUrl = "https://zenehu.space/api/custom/plants/${customPlant.id}/image",
                    cashed = false
                )
            }
            IconButton(
                onClick = {
                    navigator.push(
                        CustomCreateScreen(
                            viewModel = viewModel,
                            isCreate = false,
                            isEdit = true,
                            id = customPlant.id
                        )
                    )
                },
                content = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Edit),
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.TopEnd)
                    .size(20.dp)
            )

            IconButton(
                onClick = {
                    scope.launch {
                        customPlant.id?.let { viewModel.delCustomPlant(it) }
                        viewModel.message.collect {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.BottomEnd)
                    .size(20.dp)
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.Delete),
                    contentDescription = null
                )
            }
        }
    }
}