package com.example.zamburecetas.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MealResponse(
   @SerializedName("meals") val meals: List<Meal>
)

@Parcelize

data class Meal(
   @SerializedName("idMeal") val idMeal: String,
   @SerializedName("strMeal") val strMeal: String,
   @SerializedName("strCategory") val strCategory: String,
   @SerializedName("strInstructions") val strInstructions: String,
   @SerializedName("strMealThumb") val strMealThumb: String,
   @SerializedName("strIngredients") val strIngredients: List<String>
) : Parcelable