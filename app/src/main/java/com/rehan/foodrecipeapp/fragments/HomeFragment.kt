package com.rehan.foodrecipeapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rehan.foodrecipeapp.R
import com.rehan.foodrecipeapp.activities.CategoryMealsActivity
import com.rehan.foodrecipeapp.activities.MealActivity
import com.rehan.foodrecipeapp.adapters.CategoriesAdapter
import com.rehan.foodrecipeapp.adapters.MostPopularAdapter
import com.rehan.foodrecipeapp.databinding.FragmentHomeBinding
import com.rehan.foodrecipeapp.fragments.bottomsheet.MealBottomSheetFragment
import com.rehan.foodrecipeapp.models.MealsByCategory
import com.rehan.foodrecipeapp.models.Meal
import com.rehan.foodrecipeapp.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var mostPopularAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.rehan.foodrecipeapp.fragments.idMeal"
        const val MEAL_NAME = "com.rehan.foodrecipeapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.rehan.foodrecipeapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.rehan.foodrecipeapp.fragments.categoryName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        prepareCategoriesRecyclerView()

        viewModel.getRandomMeals()
        observeRandomMealsLiveData()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemsClick()

        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()
        onPopularItemLongClicked()
    }

    private fun preparePopularItemsRecyclerView() {
        mostPopularAdapter = MostPopularAdapter()
        binding.rvPopularItems.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopularAdapter
        }
    }

    private fun observeRandomMealsLiveData() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer {
            Glide.with(this@HomeFragment)
                .load(it.strMealThumb)
                .into(binding.ivRandomMeal)

            this.randomMeal = it        // We stored all our API response in our randomMeal variable
        })
    }

    private fun onRandomMealClick() {
        binding.ivRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            // Passing data from this fragment to activity
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner, Observer {
            mostPopularAdapter.setMeals(it as ArrayList<MealsByCategory>)
        })
    }

    // Passing data from this fragment to activity on click of recyclerview items
    private fun onPopularItemsClick() {
        mostPopularAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer {
           categoriesAdapter.setCategoryList(it)
        })
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(MEAL_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun onPopularItemLongClicked() {
        mostPopularAdapter.onLongItemClicked = {
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, " Meal Info")
        }
    }

}