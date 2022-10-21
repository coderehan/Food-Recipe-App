package com.rehan.foodrecipeapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehan.foodrecipeapp.api.RetrofitInstance
import com.rehan.foodrecipeapp.db.MealDatabase
import com.rehan.foodrecipeapp.models.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealsDatabase: MealDatabase) : ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()     // List is used because we are showing in List in RecyclerView
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var bottomSheetMealLiveData = MutableLiveData<Meal>()
    private var favouritesMealsLiveData = mealsDatabase.mealDAO().getAllMeals()

    fun getRandomMeals() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value =
                        randomMeal   // Setting api response in live data object
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Random Meal", "onFailure: " + t.message.toString())
            }

        })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealByCategoryList> {
                override fun onResponse(
                    call: Call<MealByCategoryList>,
                    response: Response<MealByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularItemsLiveData.value = response.body()!!.meals
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<MealByCategoryList>, t: Throwable) {
                    Log.d("Popular Items", "onFailure: " + t.message.toString())
                }

            })
    }

    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                // This is another way in kotlin to set response data in live data object
                response.body()?.let {
                    categoriesLiveData.postValue(it.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Category", "onFailure: " + t.message.toString())
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavouritesMealsLiveData(): LiveData<List<Meal>>{
        return favouritesMealsLiveData
    }

    fun observeBottomSheetMealLiveData(): LiveData<Meal>{
        return bottomSheetMealLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealsDatabase.mealDAO().insert(meal)
        }
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealsDatabase.mealDAO().delete(meal)
        }
    }

    fun getMealById(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object: Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {
                    bottomSheetMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Bottom Sheet", "onFailure: " + t.message.toString())
            }

        })
    }

}