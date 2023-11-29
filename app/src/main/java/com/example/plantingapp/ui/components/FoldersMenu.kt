package com.example.plantingapp.ui.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FoldersMenu(
    plant: Plant,
    setShowDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val foldersViewModel: FoldersViewModel = getViewModel()
    val folders = foldersViewModel.folders.value
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
                    text = "Выберите папку:",
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(10.dp))
                LazyColumn {
                    items(folders.size) { index ->
                        val folder = folders[index]
                        DropdownMenuItem(
                            onClick = { selectedFolder = folder },
                            enabled = (selectedFolder != folder)
                        ) {
                            Text(folders[index].folderName ?: "-")
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Интервал полива:",
                    fontSize = 18.sp
                )
                Row {
                    TextField(
                        modifier = Modifier.weight(0.5f),
                        value = num,
                        onValueChange = { num = it },
                        keyboardOptions = KeyboardOptions.Default
                            .copy(keyboardType = KeyboardType.Number),
                    )
                    ExposedDropdownMenuBox(
                        modifier = Modifier.weight(0.5f),
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        // text field
                        TextField(
                            value = selectedUnit,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(text = "Label") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            }
                        )

                        // menu
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            // this is a column scope
                            // all the items are added vertically
                            units.forEach { selectedOption ->
                                // menu item
                                DropdownMenuItem(onClick = {
                                    selectedUnit = selectedOption
                                    Toast.makeText(context, selectedOption, Toast.LENGTH_SHORT)
                                        .show()
                                    expanded = false
                                }) {
                                    Text(text = selectedOption)
                                }
                            }
                        }
                    }
                }
                Button(onClick = {
                    foldersViewModel.addPlantToFolder(selectedFolder, plant, "${num}_$selectedUnit")
                    scope.launch {
                        foldersViewModel.message.collect {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                    setShowDialog(false) }) {
                    Text("Добавить в папку")
                }
            }
        }
    }
}