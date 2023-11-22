package com.example.plantingapp.domain.models

import com.google.gson.annotations.SerializedName

data class CustomPlant (
    @SerializedName("id") val id: Int? = null,
    @SerializedName("userID") val userId: Int? = null,
    @SerializedName("plantName") val plantName: String? = null,
    @SerializedName("about") val about: String? = null
)