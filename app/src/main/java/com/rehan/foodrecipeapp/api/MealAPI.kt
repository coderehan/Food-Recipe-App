package com.rehan.foodrecipeapp.api

import com.rehan.foodrecipeapp.models.CategoryList
import com.rehan.foodrecipeapp.models.MealByCategoryList
import com.rehan.foodrecipeapp.models.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>         // This line we can write like this also -> Call<<List<Meal>>>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id: String): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealByCategoryList>

}