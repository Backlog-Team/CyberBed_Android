package com.example.plantingapp.ui.components.dialogs

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
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.plantingapp.R
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.folders.FoldersViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun ChooseFolder(
    fromFolder: Folder,
    plant: Plant,
    setShowDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val foldersViewModel: FoldersViewModel = getViewModel()
    val folders = foldersViewModel.folders.value
    val scope = rememberCoroutineScope()
    var selectedFolder by remember { mutableStateOf(Folder()) }


    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Card {
            Column(Modifier.padding(5.dp)) {
                Text(
                    text = stringResource(R.string.choose_folder),
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
                            Text(folders[index].folderName ?: stringResource(R.string.empty))
                        }
                    }
                }
                Button(onClick = {
                    foldersViewModel.changeFolder(
                        fromFolder,
                        selectedFolder,
                        plant
                    )
                    scope.launch {
                        foldersViewModel.message.collect {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                    setShowDialog(false)
                }) {
                    Text(stringResource(R.string.add_to_folder))
                }
            }
        }
    }
}
