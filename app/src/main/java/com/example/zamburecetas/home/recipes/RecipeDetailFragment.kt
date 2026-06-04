package com.example.zamburecetas.home.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.zamburecetas.core.model.Meal
import com.example.zamburecetas.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var meal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        meal = requireArguments().getParcelable("meal")
            ?: error("Meal argument required")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindMealInfo()
        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindMealInfo() {
        binding.tvTitle.text = meal.strMeal
        binding.tvCategory.text = meal.strCategory
        binding.tvIngredients.text = meal.strIngredients.joinToString("\n") { "• $it" }
        binding.tvInstructions.text = meal.strInstructions

        Glide.with(binding.ivCover)
            .load(meal.strMealThumb)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(binding.ivCover)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}