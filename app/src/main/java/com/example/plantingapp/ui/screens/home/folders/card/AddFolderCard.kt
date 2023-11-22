package com.example.plantingapp.ui.screens.home.folders.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.example.plantingapp.ui.theme.GreenBackground
import com.example.plantingapp.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFolderCard() {
    val showDialog =  remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .width(100.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreenBackground
        ),
        border = BorderStroke(
            1.dp,
            GreenPrimary
        ),
        onClick = { showDialog.value = !showDialog.value }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
             Icon(
                 modifier = Modifier.align(Alignment.Center),
                 painter = rememberVectorPainter(image = Icons.Default.Add),
                    contentDescription = null
             )

            if(showDialog.value)
                AddFolderDialog(setShowDialog = {
                    showDialog.value = it
                })
        }
    }
}