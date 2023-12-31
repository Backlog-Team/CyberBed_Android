package com.example.plantingapp.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.example.plantingapp.R
import com.example.plantingapp.ui.theme.GreenPrimary
import com.example.plantingapp.ui.theme.UnselectedGray

@Composable
fun RowScope.TabNavigationItem(tab: Tab, icSelected: Painter, icUnselected: Painter) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(
                    modifier = Modifier.size(20.dp),
                    painter = if (tabNavigator.current == tab) {
                        icSelected
                    } else {
                        icUnselected
                    },
                    contentDescription = tab.options.title
        ) },
        label = { Text(
            text = tab.options.title,
            fontSize = integerResource(id = R.integer.navbar_item_text_size).sp,
            letterSpacing = 0.1.sp
        ) },
        alwaysShowLabel = true,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Unspecified,
            selectedTextColor = GreenPrimary,
            indicatorColor = Color.White,
            unselectedIconColor = Color.Unspecified,
            unselectedTextColor = UnselectedGray
        )
    )
}