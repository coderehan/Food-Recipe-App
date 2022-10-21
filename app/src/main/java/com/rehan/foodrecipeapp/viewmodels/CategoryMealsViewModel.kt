package com.rehan.foodrecipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rehan.foodrecipeapp.api.RetrofitInstance
import com.rehan.foodrecipeapp.models.Meal
import com.rehan.foodrecipeapp.models.MealByCategoryList
import com.rehan.foodrecipeapp.models.MealsByCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {
    val categoryMealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    response.body()?.let {
                        categoryMealsLiveData.postValue(it.meals)
                    }
                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                    Log.d("Category Meals", "onFailure: " + t.message.toString())
                }

            })
    }

    fun observeCategoryMealsLiveData(): LiveData<List<MealsByCategory>>{
        return categoryMealsLiveData
    }
}