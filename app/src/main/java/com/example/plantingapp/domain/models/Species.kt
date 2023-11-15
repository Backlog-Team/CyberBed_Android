package com.example.plantingapp.domain.models

import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("ID") val id: Int = 0,
    @SerializedName("CreatedAt") val createdAt: String? = null,
    @SerializedName("UpdatedAt") val updatedAt: String? = null,
    @SerializedName("DeletedAt") val deletedAt: String? = null,
    @SerializedName("XiaomiPlantID") val xiaomiPlantId: Int = 0,
    @SerializedName("floral_language") val floralLanguage: String? = null,
    @SerializedName("origin") val origin: String? = null,
    @SerializedName("production") val production: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("blooming") val blooming: String? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("basic") val species: Species? = null
)
