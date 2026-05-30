package com.example.zamburecetas.home.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zamburecetas.core.model.Meal
import com.example.zamburecetas.databinding.ItemRecipeBinding

class RecipeAdapter(
    private val onItemClick: (Meal) -> Unit = {}
) : ListAdapter<Meal, RecipeAdapter.RecipeViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(
        private val binding: ItemRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(meal: Meal) {
            binding.tvTitle.text = meal.strMeal
            binding.tvCategory.text = meal.strCategory
            Glide.with(binding.ivCover)
                .load(meal.strMealThumb)
                .centerCrop()
                .into(binding.ivCover)
            binding.root.setOnClickListener {
                onItemClick(meal)
            }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal) =
                oldItem.idMeal == newItem.idMeal
            override fun areContentsTheSame(oldItem: Meal, newItem: Meal) =
                oldItem == newItem
        }
    }
}