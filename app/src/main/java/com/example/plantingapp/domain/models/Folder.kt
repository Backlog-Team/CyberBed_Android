package com.example.plantingapp.domain.models

import com.google.gson.annotations.SerializedName

data class Folder(
    @SerializedName("ID") val id: Int? = null,
    @SerializedName("userID") val userId: Int? = null,
    @SerializedName("folderName") val folderName: String? = null,
    @SerializedName("plantsNum") val plantsNum: Int? = null
)
