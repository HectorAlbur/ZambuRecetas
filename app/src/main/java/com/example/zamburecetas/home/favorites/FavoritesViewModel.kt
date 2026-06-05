package com.example.zamburecetas.home.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zamburecetas.home.favorites.model.FavoriteRecipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavoritesViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val uid get() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    private val _favorites = MutableStateFlow<List<FavoriteRecipe>>(emptyList())
    val favorites: StateFlow<List<FavoriteRecipe>> = _favorites.asStateFlow()

    init { loadFavorites() }

    fun loadFavorites() {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("users").document(uid)
                    .collection("favoritos")
                    .orderBy("addedAt", Query.Direction.DESCENDING)
                    .get().await()
                _favorites.value = snapshot.documents.mapNotNull {
                    it.toObject(FavoriteRecipe::class.java)
                }
            } catch (e: Exception) {
                _favorites.value = emptyList()
            }
        }
    }

    fun removeFavorite(item: FavoriteRecipe) {
        viewModelScope.launch {
            try {
                db.collection("users").document(uid)
                    .collection("favoritos")
                    .document(item.idMeal)
                    .delete().await()
                loadFavorites()
            } catch (e: Exception) { }
        }
    }
}