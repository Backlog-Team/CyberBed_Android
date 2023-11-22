package com.example.plantingapp.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.LoadingStates
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
    var loading by remember { mutableStateOf(false) }
    var showErrMsg by remember { mutableStateOf(false) }
    val errMsg = viewModel.message.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    var passwordsEqual by remember { mutableStateOf(true) }

    when (loadingState.value) {
        LoadingStates.Success -> {
            navigator.replace(BaseScreen())
            showErrMsg = false
            loading = false
        }

        LoadingStates.Loading -> {
            showErrMsg = false
            loading = true
        }

        LoadingStates.Error -> {
            showErrMsg = true
            loading = false
        }

        else -> {
            showErrMsg = false
            loading = false
        }
    }

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

        if (!passwordsEqual) {
            Text(
                text = "Пароли не совпадают",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red
            )
        }
        if (showErrMsg) {
            Text(
                text = errMsg.value,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red
            )
        }
        if (loading) {
            CircularProgressIndicator()
        }
        Button(
            onClick = {
                if (password == passwordRepeat)
                    viewModel.signup(username, password)
                else {
                    passwordsEqual = false

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }
    }
}