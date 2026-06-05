package com.example.zamburecetas.home.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zamburecetas.R
import com.example.zamburecetas.core.model.Meal
import com.example.zamburecetas.databinding.FragmentFavoritesBinding
import com.example.zamburecetas.home.favorites.model.FavoriteRecipe
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoritesViewModel>()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeState()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter(
            onItemClick = { favorite -> navigateToDetail(favorite) },
            onRemoveClick = { favorite -> viewModel.removeFavorite(favorite) }
        )
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = adapter
    }

    private fun navigateToDetail(favorite: FavoriteRecipe) {
        val meal = Meal(
            idMeal = favorite.idMeal,
            strMeal = favorite.strMeal,
            strCategory = favorite.strCategory,
            strInstructions = favorite.strInstructions,
            strMealThumb = favorite.strMealThumb,
            strIngredients = favorite.strIngredients
        )
        val bundle = Bundle().apply { putParcelable("meal", meal) }
        findNavController().navigate(
            R.id.action_favoritesFragment_to_recipeDetailFragment, bundle
        )
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { favorites ->
                    adapter.submitList(favorites)
                    if (favorites.isEmpty()) {
                        binding.rvFavorites.visibility = View.GONE
                        binding.layoutEmpty.visibility = View.VISIBLE
                    } else {
                        binding.rvFavorites.visibility = View.VISIBLE
                        binding.layoutEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}