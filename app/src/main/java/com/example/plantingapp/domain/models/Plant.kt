package com.example.plantingapp.domain.models

import android.graphics.Bitmap
import com.example.plantingapp.ui.LoadingStates
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow

data class Plant (
    @SerializedName("CreatedAt") val createdAt: String? = null,
    @SerializedName("UpdatedAt") val updatedAt: String? = null,
    @SerializedName("DeletedAt") val deletedAt: String? = null,
    @SerializedName("ID") val id: Int = 0,
    @SerializedName("pid") val pid: String? = null,
    @SerializedName("basic") val species: Species? = null,
    @SerializedName("display_pid") val displayPid: String? = null,
    @SerializedName("maintenance") val maintenance: Maintenance? = null,
    @SerializedName("parameter") val parameter: Parameter? = null,
    var bitImage: Bitmap? = null,
    var _loadingState: MutableStateFlow<LoadingStates> = MutableStateFlow(LoadingStates.Loading)
)