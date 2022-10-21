package com.rehan.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehan.foodrecipeapp.databinding.AdapterCategoryItemsBinding
import com.rehan.foodrecipeapp.databinding.AdapterCategoryMealsItemsBinding
import com.rehan.foodrecipeapp.models.Category
import com.rehan.foodrecipeapp.models.MealsByCategory

class CategoryMealsAdapter(): RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {

    private var categoryMealsList = ArrayList<MealsByCategory>()

    fun setCategoryList(categoryMealsList: List<MealsByCategory>){
        this.categoryMealsList = categoryMealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterCategoryMealsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryMealsList[position].strMealThumb)
            .into(holder.binding.ivCategory)
        holder.binding.tvCategoryMealsName.text = categoryMealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return categoryMealsList.size
    }

    class ViewHolder(var binding: AdapterCategoryMealsItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}