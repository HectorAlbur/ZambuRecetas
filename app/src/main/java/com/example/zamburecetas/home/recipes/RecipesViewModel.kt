package com.example.zamburecetas.home.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zamburecetas.core.ResponseService
import com.example.zamburecetas.core.model.Meal
import com.example.zamburecetas.core.network.MealService
import com.example.zamburecetas.core.repositories.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val service: MealService = MealRepository()
) : ViewModel() {

    private val _mealState = MutableStateFlow<ResponseService<List<Meal>>?>(null)
    val mealState: StateFlow<ResponseService<List<Meal>>?> = _mealState.asStateFlow()

    fun loadMeals() {
        viewModelScope.launch {
            _mealState.value = ResponseService.Loading
            _mealState.value = service.getMeals()
        }
    }
}