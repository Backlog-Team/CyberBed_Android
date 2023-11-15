package com.example.plantingapp.domain.models

import com.google.gson.annotations.SerializedName

data class Maintenance(
    @SerializedName("ID") val id: Int = 0,
    @SerializedName("CreatedAt") val createdAt: String? = null,
    @SerializedName("UpdatedAt") val updatedAt: String? = null,
    @SerializedName("DeletedAt") val deletedAt: String? = null,
    @SerializedName("XiaomiPlantID") val xiaomiPlantID: Int = 0,
    @SerializedName("size") val size: String? = null,
    @SerializedName("soil") val soil: String? = null,
    @SerializedName("sunlight") val sunlight: String? = null,
    @SerializedName("watering") val watering: String? = null,
    @SerializedName("fertilization") val fertilization: String? = null,
    @SerializedName("pruning") val pruning: String? = null
)
