package com.example.plantingapp.ui.screens.home.folders.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.plantingapp.ui.screens.home.folders.FoldersViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AddFolderDialog(
    setShowDialog: (Boolean) -> Unit
) {
    val foldersViewModel: FoldersViewModel = getViewModel()
    var folderName by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Card() {
            Column(Modifier.padding(5.dp)) {
                Text(
                    text = "Введите название:",
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(10.dp))

                OutlinedTextField(value = folderName, onValueChange = { folderName = it })
                Button(onClick = {
                    foldersViewModel.createFolder(folderName)
                    setShowDialog(false)
                }) {
                    Text("OK")
                }
            }
        }
    }
}