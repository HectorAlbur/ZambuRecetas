package com.example.zamburecetas.core.network

import com.example.zamburecetas.core.ResponseService
import com.example.zamburecetas.core.model.Meal

interface MealService {
    suspend fun getMeals(): ResponseService<List<Meal>>
}