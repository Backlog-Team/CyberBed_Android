package com.example.plantingapp.management

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class NavbarManager {
    var navbarState: MutableState<Boolean> = mutableStateOf(true)

    fun showNavbar() {
        navbarState.value = true
    }

    fun hideNavbar() {
        navbarState.value = false
    }
}