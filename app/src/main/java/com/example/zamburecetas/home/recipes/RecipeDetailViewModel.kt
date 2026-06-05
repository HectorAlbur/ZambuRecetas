package com.example.zamburecetas.home.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zamburecetas.core.model.Meal
import com.example.zamburecetas.home.favorites.model.FavoriteRecipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RecipeDetailViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val uid get() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun checkFavorite(mealId: String) {
        viewModelScope.launch {
            try {
                val doc = db.collection("users").document(uid)
                    .collection("favoritos").document(mealId).get().await()
                _isFavorite.value = doc.exists()
            } catch (e: Exception) {
                _isFavorite.value = false
            }
        }
    }

    fun toggleFavorite(meal: Meal) {
        viewModelScope.launch {
            try {
                val docRef = db.collection("users").document(uid)
                    .collection("favoritos").document(meal.idMeal)
                if (_isFavorite.value) {
                    docRef.delete().await()
                    _isFavorite.value = false
                } else {
                    val favorite = FavoriteRecipe(
                        idMeal = meal.idMeal,
                        strMeal = meal.strMeal,
                        strCategory = meal.strCategory,
                        strInstructions = meal.strInstructions,
                        strMealThumb = meal.strMealThumb,
                        strIngredients = meal.strIngredients,
                        addedAt = System.currentTimeMillis()
                    )
                    docRef.set(favorite).await()
                    _isFavorite.value = true
                }
            } catch (e: Exception) { }
        }
    }
}