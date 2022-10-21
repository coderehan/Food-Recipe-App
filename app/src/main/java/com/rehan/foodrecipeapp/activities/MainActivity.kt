package com.rehan.foodrecipeapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rehan.foodrecipeapp.R
import com.rehan.foodrecipeapp.databinding.ActivityMainBinding
import com.rehan.foodrecipeapp.db.MealDatabase
import com.rehan.foodrecipeapp.viewmodels.HomeViewModel
import com.rehan.foodrecipeapp.viewmodels.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: HomeViewModel by lazy{
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // In the activity, when user click on different bottom tabs we should navigate them to respective fragments
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

    }
}