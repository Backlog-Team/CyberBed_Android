package com.example.plantingapp.ui.components.dialogs

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.plantingapp.R
import com.example.plantingapp.ui.screens.settings.bluetooth.BluetoothViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChooseChannel(
    setShowDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val btViewModel: BluetoothViewModel = getViewModel()

    val scope = rememberCoroutineScope()

    val channel1 = stringResource(R.string.channel_1)
    val channel2 = stringResource(R.string.channel_2)
    val channels = listOf(channel1, channel2)
    var selectedChannel by remember { mutableStateOf("") }

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
                            onClick = {
                                scope.launch {
                                    selectedChannel = channel
                                    if (selectedChannel == channel1)
                                        btViewModel.sendChannel1()
                                    else if (selectedChannel == channel2)
                                        btViewModel.sendChannel2()
                                    btViewModel.toast.collect {
                                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    }
                                }
                                setShowDialog(false)
                            }
                        ) {
                            Text(channel)
                        }
                    }
                }
            }
        }
    }
}