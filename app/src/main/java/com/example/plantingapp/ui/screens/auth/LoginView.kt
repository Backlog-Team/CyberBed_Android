@file:JvmName("LoginViewKt")

package com.example.plantingapp.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.plantingapp.ui.screens.BaseScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    viewModel: AuthViewModel
) {
    val navigator = LocalNavigator.currentOrThrow
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
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
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = {
                Text("Password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                        Log.d("kilo", "Password visibility: $passwordVisibility")
                    },
                    enabled = passwordVisibility,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.DarkGray,
                        disabledContentColor = Color.LightGray,
                    )
                ) {
                    Icon(
                        modifier = Modifier.clickable { passwordVisibility = !passwordVisibility },
                        painter = rememberVectorPainter(Icons.Default.Visibility),
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            }  else PasswordVisualTransformation()
        )
        Button(
            onClick = {
                viewModel.login(username, password)
                navigator.replace(BaseScreen())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }
        Text(
            modifier = Modifier.clickable { navigator.push(SignupScreen(viewModel)) },
            text = "Нет аккаунта? Зарегистрируйтесь!",
            textDecoration = TextDecoration.Underline,
            fontSize = 14.sp
        )
    }
}