package com.example.zamburecetas.home.favorites.model

data class FavoriteRecipe(
    val idMeal: String = "",
    val strMeal: String = "",
    val strCategory: String = "",
    val strInstructions: String = "",
    val strMealThumb: String = "",
    val strIngredients: List<String> = emptyList(),
    val addedAt: Long = 0
)