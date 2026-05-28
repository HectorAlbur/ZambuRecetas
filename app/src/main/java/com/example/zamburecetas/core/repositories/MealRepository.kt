package com.example.zamburecetas.core.repositories

import com.example.zamburecetas.core.ResponseService
import com.example.zamburecetas.core.model.Meal
import com.example.zamburecetas.core.network.ApiClient
import com.example.zamburecetas.core.network.MealService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealRepository : MealService {

    private val api = ApiClient.MealAPI

    override suspend fun getMeals(): ResponseService<List<Meal>> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getMeals()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        ResponseService.Success(body.meals)
                    } else {
                        ResponseService.Error("Respuesta vacía del servidor")
                    }
                } else {
                    ResponseService.Error("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                ResponseService.Error(
                    "No se pudieron cargar las recetas: ${e.localizedMessage}"
                )
            }
        }
}