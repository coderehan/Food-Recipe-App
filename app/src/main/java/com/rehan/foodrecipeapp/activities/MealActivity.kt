package com.rehan.foodrecipeapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rehan.foodrecipeapp.R
import com.rehan.foodrecipeapp.databinding.ActivityMainBinding
import com.rehan.foodrecipeapp.databinding.ActivityMealBinding
import com.rehan.foodrecipeapp.db.MealDatabase
import com.rehan.foodrecipeapp.fragments.HomeFragment
import com.rehan.foodrecipeapp.models.Meal
import com.rehan.foodrecipeapp.viewmodels.MealDetailsViewModel
import com.rehan.foodrecipeapp.viewmodels.MealDetailsViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var viewModel: MealDetailsViewModel
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youTubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealDetailsViewModelFactory(mealDatabase)
        viewModel = ViewModelProvider(this, viewModelFactory)[MealDetailsViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()
        viewModel.getMealDetails(mealId)
        observeMealDetailsLiveData()
        onYoutubeImageClick()
        onFavouriteClick()
    }

    private fun getMealInformationFromIntent() {
        // Getting data from fragment and setting data in this activity
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.ivMealDetail)

        binding.collapsingToolBar.title = mealName  // Setting title text in toolbar
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white)) // Setting title text color in toolbar
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private var mealToSave: Meal? = null

    private fun observeMealDetailsLiveData() {
        viewModel.observeMealDetailsLiveData().observe(this, Observer {
            onResponseCase()
            mealToSave = it     // Storing API success response in this variable so that we can use for room database
            binding.tvCategory.text = "Category : ${it.strCategory}"
                binding.tvArea.text = "Area : ${it.strArea}"
                binding.tvInstructionsResponse.text = it.strInstructions
                youTubeLink = it.strYoutube.toString()
        })
    }

    // When our progress bar is loading, we will perform this function
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.fab.visibility = View.GONE
        binding.tvCategory.visibility = View.GONE
        binding.tvArea.visibility = View.GONE
        binding.tvInstructionsResponse.visibility = View.GONE
        binding.ivYoutube.visibility = View.GONE
    }

    // When we get response from API, we will hide progress bar
    private fun onResponseCase(){
        binding.progressBar.visibility = View.GONE
        binding.fab.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvInstructionsResponse.visibility = View.VISIBLE
        binding.ivYoutube.visibility = View.VISIBLE
    }

    private fun onYoutubeImageClick() {
        binding.ivYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }
    }

    private fun onFavouriteClick() {
        binding.fab.setOnClickListener {
            mealToSave?.let {
                viewModel.insertMeal(it)
                Toast.makeText(this, "Meal saved successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}