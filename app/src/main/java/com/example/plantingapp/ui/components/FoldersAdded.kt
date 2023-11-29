package com.example.plantingapp.ui.components

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FoldersAdded(
    plant: Plant,
    setShowDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val folderViewModel: FoldersViewModel = getViewModel()
    val folders = plant.folder
    val scope = rememberCoroutineScope()

    var num by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val units = listOf("s", "m", "h", "d")
    var selectedUnit by remember { mutableStateOf(units[0]) }
    var selectedFolder by remember { mutableStateOf(Folder()) }


    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Card {
            Column(Modifier.padding(5.dp)) {
                Text(
                    text = "Добавлено в папки:",
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(10.dp))
                LazyColumn {
                    if (folders != null) {
                        items(folders.size) { index ->
                            val folder = folders[index]
                            DropdownMenuItem(
                                onClick = {
                                    scope.launch {
                                        folderViewModel.delPlantsFromFolder(folder, plant)
                                        folderViewModel.message.collect {
                                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    setShowDialog(false)
                                },
                                enabled = (selectedFolder != folder)
                            ) {
                                Text(folders[index].folderName ?: "-")
                            }
                        }
                    }
                }
            }
        }
    }
}