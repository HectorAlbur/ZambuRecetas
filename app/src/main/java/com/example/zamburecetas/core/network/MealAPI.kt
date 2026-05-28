package com.example.zamburecetas.core.network

import com.example.zamburecetas.core.model.MealResponse
import retrofit2.Response
import retrofit2.http.GET

interface MealAPI {
    @GET("/")
    suspend fun getMeals(): Response<MealResponse>
}