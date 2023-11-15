package com.example.plantingapp.domain.models

import com.google.gson.annotations.SerializedName

data class Parameter(
    @SerializedName("ID") val id: Int = 0,
    @SerializedName("CreatedAt") val createdAt: String? = null,
    @SerializedName("UpdatedAt") val updatedAt: String? = null,
    @SerializedName("DeletedAt") val deletedAt: String? = null,
    @SerializedName("XiaomiPlantID") val xiaomiPlantID: Int = 0,
    @SerializedName("max_light_mmol") val maxLightmmol: Int = 0,
    @SerializedName("min_light_mmol") val minLightmmol: Int = 0,
    @SerializedName("max_light_lux") val maxLightLux: Int = 0,
    @SerializedName("min_light_lux") val minLightLux: Int = 0,
    @SerializedName("max_temp") val maxTemp: Int = 0,
    @SerializedName("min_temp") val minTemp: Int = 0,
    @SerializedName("max_env_humidity") val maxEnvHumidity: Int = 0,
    @SerializedName("min_env_humidity") val minEnvHumidity: Int = 0,
    @SerializedName("max_soil_moisture") val maxSoilMoisture: Int = 0,
    @SerializedName("min_soil_moisture") val minSoilMoisture: Int = 0,
    @SerializedName("max_soil_ec") val maxSoilEc: Int = 0,
    @SerializedName("min_soil_ec") val minSoilEc: Int = 0,
)
