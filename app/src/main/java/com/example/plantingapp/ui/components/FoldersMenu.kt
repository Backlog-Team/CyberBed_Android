package com.example.plantingapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun FoldersMenu(
    plant: Plant,
    setShowDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val foldersViewModel: FoldersViewModel = getViewModel()
    val folders = foldersViewModel.folders
    val scope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Card() {
            Column(Modifier.padding(5.dp)) {
                Text(
                    text = "Выберите папку:",
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(10.dp))
                LazyColumn {
                    items(folders.value.size) { index ->
                        DropdownMenuItem(onClick = {
                            foldersViewModel.addPlantToFolder(folders.value[index], plant)
                            scope.launch {
                                foldersViewModel.message.collect {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                }
                            }
                            setShowDialog(false)
                        }) {
                            Text(folders.value[index].folderName ?: "-")
                        }
                    }
                }
            }
        }
    }
}