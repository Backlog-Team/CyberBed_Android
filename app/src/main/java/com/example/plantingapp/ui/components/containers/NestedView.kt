package com.example.plantingapp.ui.components.containers

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.plantingapp.management.NavbarManager
import org.koin.compose.koinInject

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NestedView(
    modifier: Modifier = Modifier,
    isDarkBackground: Boolean = false,
    onClose: (() -> Unit),
    content: @Composable (BoxScope.() -> Unit),
    ) {
    val navbarManager: NavbarManager = koinInject()
    navbarManager.hideNavbar()
    val window = (LocalContext.current as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    window.isStatusBarContrastEnforced = true
    Box(modifier = modifier.fillMaxSize()) {
        content()
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onClose
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.Default.Close),
                contentDescription = null,
                tint = if (isDarkBackground) Color.White else Color.Black,
                modifier = Modifier.systemBarsPadding()
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.1f))
                    .padding(10.dp)
            )
        }
    }
}