package com.rehan.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehan.foodrecipeapp.databinding.AdapterPopularItemsBinding
import com.rehan.foodrecipeapp.models.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {

    private var categoryMealsList = ArrayList<MealsByCategory>()
    lateinit var onItemClick: ((MealsByCategory) -> Unit)
    var onLongItemClicked: ((MealsByCategory) -> Unit)? = null

    fun setMeals(categoryMealsList: ArrayList<MealsByCategory>) {
        this.categoryMealsList = categoryMealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterPopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryMealsList[position].strMealThumb)
            .into(holder.binding.ivPopularItems)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoryMealsList[position])

            holder.itemView.setOnLongClickListener {
                onLongItemClicked?.invoke(categoryMealsList[position])
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryMealsList.size
    }

    class ViewHolder(var binding: AdapterPopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}