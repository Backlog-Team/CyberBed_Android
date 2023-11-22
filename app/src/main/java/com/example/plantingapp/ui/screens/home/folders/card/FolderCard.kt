package com.example.plantingapp.ui.screens.home.folders.card

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel
import com.example.plantingapp.ui.screens.home.folders.details.FolderDetailsScreen
import com.example.plantingapp.ui.theme.GreenBackground
import org.koin.androidx.compose.getViewModel

@Composable
fun FolderCard(folder: Folder) {
    val navigator = LocalNavigator.current
    val foldersViewModel: FoldersViewModel = getViewModel()

    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(80.dp)
            .width(160.dp)
            .clickable {
                navigator?.push(
                    FolderDetailsScreen(
                        folder,
                        foldersViewModel
                    )
                )
            },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreenBackground
        ),
        /*border = BorderStroke(
            1.dp,
            GreenPrimary
        )*/
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
                        modifier = Modifier.align(Alignment.Bottom),
                        onClick = {
                            foldersViewModel.delFolder(folder)
                        },
                        content = {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Delete),
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
                        text = folder.folderName ?: "-",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = "Растений: ${folder.plantsNum}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                    )
                }
            }
        }
    }
}