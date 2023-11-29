package com.example.plantingapp.ui.screens.explore.custom.create

import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.components.containers.NestedView

@Composable
fun CustomCreateView(
    viewModel: CustomCreateViewModel,
    isCreate: Boolean,
    isEdit: Boolean,
    id: Int?
) {
    val loadingState = viewModel.loadingState.collectAsState()

    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var descr by remember { mutableStateOf("") }

    var chosenPic: Bitmap? = null

    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            chosenPic = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    NestedView(onClose = { navigator.pop() }) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Название") }
            )
            OutlinedTextField(
                value = descr,
                onValueChange = { descr = it },
                label = { Text("Описание") }
            )
            Button(
                onClick = {
                    pickMedia.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                    chosenPic?.let {
                        Toast.makeText(context, "Изображение выбрано", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                content = {
                    Row {
                        Text("Добавить изображение")
                        Icon(
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                            painter = rememberVectorPainter(image = Icons.Default.Image)
                        )
                    }
                }
            )

            Spacer(Modifier.height(50.dp))
            if (isCreate) {
                Button(
                    onClick = {
                        viewModel.createCustomPlant(name, descr, chosenPic)
                        navigator.pop()
                    }
                ) {
                    Text("Создать свое растение")
                }
            } else if (isEdit) {
                Button(
                    onClick = {
                        if (id != null) {
                            viewModel.changeCustomPlant(id, name, descr, chosenPic)
                        }
                        navigator.pop()
                    }
                ) {
                    Text("Изменить свое растение")
                }
            }
        }
    }
}