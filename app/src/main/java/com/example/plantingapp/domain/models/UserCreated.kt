package com.example.plantingapp.domain.models

import com.google.gson.annotations.SerializedName

data class UserCreated(
    @SerializedName("userID") val id: Int = 0
)
