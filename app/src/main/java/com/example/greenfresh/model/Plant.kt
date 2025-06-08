package com.example.greenfresh.model

import com.google.gson.annotations.SerializedName

data class Plant(
    @SerializedName("id")
    val id: Int,

    @SerializedName("plant_name")
    val plantName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
) {
    // Helper function to format price
    fun getFormattedPrice(): String {
        return try {
            val priceNumber = price.toLong()
            "Rp ${String.format("%,d", priceNumber).replace(',', '.')}"
        } catch (e: NumberFormatException) {
            "Rp $price"
        }
    }
}

data class PlantResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Plant>
)

data class SinglePlantResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: Plant
)