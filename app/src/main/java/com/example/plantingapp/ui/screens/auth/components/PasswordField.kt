package com.example.plantingapp.ui.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.plantingapp.R

@Composable
fun PasswordField(
    label: String = stringResource(R.string.password),
    password: String = "",
    onValueChange: (String) -> Unit,
    visibility: Boolean = false,
    onVisibilityChange: () -> Unit
    ) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            IconButton(
                onClick = onVisibilityChange,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.DarkGray,
                    disabledContentColor = Color.LightGray,
                )
            ) {
                Icon(
                    painter = rememberVectorPainter(
                        if (visibility) Icons.Default.Visibility
                    else Icons.Default.VisibilityOff),
                    contentDescription = null
                )
            }
        },
        visualTransformation = if (visibility) {
            VisualTransformation.None
        }  else PasswordVisualTransformation()
    )
}