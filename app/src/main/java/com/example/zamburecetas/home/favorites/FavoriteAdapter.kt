package com.example.zamburecetas.home.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zamburecetas.databinding.ItemFavoriteBinding
import com.example.zamburecetas.home.favorites.model.FavoriteRecipe

class FavoriteAdapter(
    private val onItemClick: (FavoriteRecipe) -> Unit = {},
    private val onRemoveClick: (FavoriteRecipe) -> Unit = {}
) : ListAdapter<FavoriteRecipe, FavoriteAdapter.FavoriteViewHolder>(DIFF) {

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavoriteRecipe) {
            binding.tvTitle.text = item.strMeal
            binding.tvCategory.text = item.strCategory
            Glide.with(binding.ivCover)
                .load(item.strMealThumb)
                .centerCrop()
                .placeholder(android.R.color.darker_gray)
                .into(binding.ivCover)
            binding.root.setOnClickListener { onItemClick(item) }
            binding.btnRemove.setOnClickListener { onRemoveClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<FavoriteRecipe>() {
            override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe) =
                oldItem.idMeal == newItem.idMeal
            override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe) =
                oldItem == newItem
        }
    }
}