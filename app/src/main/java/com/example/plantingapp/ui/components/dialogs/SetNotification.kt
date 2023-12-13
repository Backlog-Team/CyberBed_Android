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
import com.example.plantingapp.domain.models.Channel
import com.example.plantingapp.domain.models.Folder
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.ui.screens.folders.FoldersViewModel
import com.example.plantingapp.ui.screens.saved.SavedViewModel
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SetNotification(
    plant: Plant,
    setShowDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val savedViewModel: SavedViewModel = getViewModel()
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
    val channel1 = stringResource(R.string.channel_1)
    val channel2 = stringResource(R.string.channel_2)
    val channels = listOf(channel1, channel2)
    var selectedChannel by remember { mutableStateOf(channel1) }

    val btViewModel: BluetoothViewModel = getViewModel()

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Card {
            Column(Modifier.padding(5.dp)) {
                Text(
                    text = stringResource(R.string.choose_channel),
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(10.dp))
                LazyColumn {
                    items(channels.size) { index ->
                        val channel = channels[index]
                        DropdownMenuItem(
                            onClick = { selectedChannel = channel },
                            enabled = (selectedChannel != channel)
                        ) {
                            Text(channel ?: stringResource(R.string.empty))
                        }
                    }
                }
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
                    savedViewModel.savePlant(
                        plant,
                        "${num}_${units[selectedUnit]}"
                    )
                    savedViewModel.setChannel(plant, Channel(if (selectedChannel == channel1)
                        1 else 2))
                    scope.launch {
                        savedViewModel.toastMessage.collect {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                    setShowDialog(false)
                }) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}