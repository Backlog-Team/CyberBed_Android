package com.example.plantingapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.LoadingStates
import com.example.plantingapp.ui.components.GroupHeader
import com.example.plantingapp.ui.components.ScanButton
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel
import com.example.plantingapp.ui.screens.home.folders.card.AddFolderCard
import com.example.plantingapp.ui.screens.home.folders.card.FolderCard
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeView() {
    val navigator = LocalNavigator.currentOrThrow
    val foldersViewModel: FoldersViewModel = getViewModel()
    val folders = foldersViewModel.folders.collectAsState()
    val loadingState = foldersViewModel.loadingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "My Plants",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
        ScanButton()

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Folders",
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                fontWeight = FontWeight.Medium,
            )
            IconButton(
                onClick = {
                    foldersViewModel.getFolders()
                },
                content = {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.Refresh),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )

                }
            )
        }
        when (loadingState.value) {
            LoadingStates.Success -> {
                LazyRow {
                    items(folders.value.size) { index ->
                        FolderCard(folders.value[index])
                    }
                    item {
                        AddFolderCard()
                    }
                }
            }
            LoadingStates.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Не удалось загрузить папки")
                    Button(onClick = { foldersViewModel.getFolders() }) {
                        Text("Попробовать еще раз")
                    }
                }
            }
            LoadingStates.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }
            LoadingStates.NotLoading -> {
                Text("Загрузка еще не началася")
            }
        }
        Divider(color = Color.LightGray, thickness = 1.dp)
        GroupHeader("Notifications") {}
        Text("Coming soon...")
    }
}