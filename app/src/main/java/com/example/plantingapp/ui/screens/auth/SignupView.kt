package com.example.plantingapp.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.screens.BaseScreen
import com.example.plantingapp.ui.screens.auth.components.PasswordField

@Composable
fun SignupView(
    viewModel: AuthViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordRepeatVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 120.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CyberBed",
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = {
                Text("Username")
            }
        )
        PasswordField(
            password = password,
            onValueChange = { password = it },
            visibility = passwordVisibility,
            onVisibilityChange = { passwordVisibility = !passwordVisibility })

        PasswordField(
            label = "Repeat",
            password = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            visibility = passwordRepeatVisibility,
            onVisibilityChange = { passwordRepeatVisibility = !passwordRepeatVisibility })

        Button(
            onClick = {
                viewModel.signup(username, password)
                navigator.replace(BaseScreen())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться")
        }
    }
}