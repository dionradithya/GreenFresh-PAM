package com.example.greenfresh.api

import com.example.greenfresh.model.PlantResponse
import com.example.greenfresh.model.SinglePlantResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("plant/all")
    fun getAllPlants(): Call<PlantResponse>

    @GET("plant/{plant_name}")
    fun getPlantByName(@Path("plant_name") plantName: String): Call<SinglePlantResponse>

    @POST("plant/new")
    fun createPlant(@Body plant: CreatePlantRequest): Call<SinglePlantResponse>

    @PUT("plant/{plant_name}")
    fun updatePlant(
        @Path("plant_name") plantName: String,
        @Body plant: CreatePlantRequest
    ): Call<SinglePlantResponse>

    @DELETE("plant/{plant_name}")
    fun deletePlant(@Path("plant_name") plantName: String): Call<Void>
}

data class CreatePlantRequest(
    val plant_name: String,
    val description: String,
    val price: String
)