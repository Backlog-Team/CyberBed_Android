package com.example.plantingapp.domain.models

import com.google.gson.annotations.SerializedName

data class Plant (
    @SerializedName("CreatedAt") val createdAt: String? = null,
    @SerializedName("UpdatedAt") val updatedAt: String? = null,
    @SerializedName("DeletedAt") val deletedAt: String? = null,
    @SerializedName("ID") val id: Int = 0,
    @SerializedName("pid") val pid: String? = null,
    @SerializedName("basic") val basic: Basic? = null,
    @SerializedName("display_pid") val displayPid: String? = null,
    @SerializedName("maintenance") val maintenance: Maintenance? = null,
    @SerializedName("parameter") val parameter: Parameter? = null,
)