package com.rehan.foodrecipeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rehan.foodrecipeapp.db.MealDatabase

class MealDetailsViewModelFactory(private val mealDatabase: MealDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailsViewModel(mealDatabase) as T
    }
}