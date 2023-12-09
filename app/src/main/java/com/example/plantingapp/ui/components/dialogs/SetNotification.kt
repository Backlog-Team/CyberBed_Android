package com.example.plantingapp.ui.components.dialogs

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
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

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SetNotification(
    plant: Plant,
    setShowDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val foldersViewModel: FoldersViewModel = getViewModel()
    val folders = foldersViewModel.folders.value
    val scope = rememberCoroutineScope()

    var num by remember { mutableStateOf("0") }
    var expanded by remember { mutableStateOf(false) }
    val units = mapOf(
        stringResource(R.string.seconds_abbr) to "s",
        stringResource(R.string.minutes_abbr) to "m",
        stringResource(R.string.hours_abbr) to "h",
        pluralStringResource(R.plurals.days, num.toInt()) to "d"
    )
    var selectedUnit by remember { mutableStateOf(units.keys.first()) }
    var selectedFolder by remember { mutableStateOf(Folder()) }


    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Card {
            Column(Modifier.padding(5.dp)) {
                Text(
                    text = stringResource(R.string.watering_interval),
                    fontSize = 18.sp
                )
                Row {
                    TextField(
                        modifier = Modifier.weight(0.7f),
                        value = num,
                        onValueChange = { num = if (it.toIntOrNull() != null) it else num},
                        keyboardOptions = KeyboardOptions.Default
                            .copy(keyboardType = KeyboardType.Decimal)
                    )
                    ExposedDropdownMenuBox(
                        modifier = Modifier.weight(0.3f),
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        // text field
                        TextField(
                            value = selectedUnit,
                            onValueChange = { selectedUnit = it },
                            readOnly = true,
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
                                    selectedUnit = selectedOption.key
                                    expanded = false
                                }) {
                                    Text(text = selectedOption.value)
                                }
                            }
                        }
                    }
                }
                Button(onClick = {
                    foldersViewModel.addPlantToFolder(
                        selectedFolder,
                        plant,
                        "${num}_${units[selectedUnit]}"
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