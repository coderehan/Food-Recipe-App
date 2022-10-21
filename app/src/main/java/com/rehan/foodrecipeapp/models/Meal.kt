package com.rehan.foodrecipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mealsInformation")
data class Meal(
    val dataModified: Any?,
    @PrimaryKey     // We didn't set autoGenerate = true because we are getting this id from API
    val idMeal: String,
    val strMeal: String?,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strYoutube: String?,
    val strMealThumb: String?
)
