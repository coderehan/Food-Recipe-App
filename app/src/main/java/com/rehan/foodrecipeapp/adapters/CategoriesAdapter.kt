package com.rehan.foodrecipeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rehan.foodrecipeapp.databinding.AdapterCategoryItemsBinding
import com.rehan.foodrecipeapp.models.Category
import com.rehan.foodrecipeapp.models.MealsByCategory

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var categoriesList = ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)? = null

    fun setCategoryList(categoryList: List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterCategoryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.ivCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    class ViewHolder(var binding: AdapterCategoryItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}