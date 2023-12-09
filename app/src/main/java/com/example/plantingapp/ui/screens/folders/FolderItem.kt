package com.example.plantingapp.ui.screens.folders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.R
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.ui.screens.folders.details.FolderDetailsScreen

@Composable
fun FolderItem(folder: Folder, foldersViewModel: FoldersViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                navigator.push(
                    FolderDetailsScreen(
                        folder,
                        foldersViewModel
                    )
                )
            }
            .clip(RoundedCornerShape(12.dp))
            .background(if (folder.isDefault == true) Color(0xFFC390E6) else Color(0xFFF8F8F8))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = folder.folderName ?: stringResource(id = R.string.empty),
            fontSize = 16.sp)
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .size(15.dp)
                .align(Alignment.CenterVertically),
            tint = Color.Black
        )
    }
}