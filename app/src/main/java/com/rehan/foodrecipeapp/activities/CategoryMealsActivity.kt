package com.rehan.foodrecipeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rehan.foodrecipeapp.R
import com.rehan.foodrecipeapp.adapters.CategoryMealsAdapter
import com.rehan.foodrecipeapp.adapters.MostPopularAdapter
import com.rehan.foodrecipeapp.databinding.ActivityCategoryMealsBinding
import com.rehan.foodrecipeapp.databinding.ActivityMealBinding
import com.rehan.foodrecipeapp.fragments.HomeFragment
import com.rehan.foodrecipeapp.viewmodels.CategoryMealsViewModel
import com.rehan.foodrecipeapp.viewmodels.MealDetailsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var viewModel: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_meals)

        prepareCategoryMealsRecyclerView()

        viewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.MEAL_NAME)!!)
        viewModel.observeCategoryMealsLiveData().observe(this, Observer {
            binding.tvCategoriesMeals.text = it.size.toString()     // Displaying number of items in that particular category
            categoryMealsAdapter.setCategoryList(it)
        })
    }

    private fun prepareCategoryMealsRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvCategoryMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}