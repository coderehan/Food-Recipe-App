package com.rehan.foodrecipeapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    // This is used to convert Any datatype to String datatype
    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        if (attribute == null) {
            return ""
        }
        return attribute as String
    }

    // This is used to convert String datatype to Any datatype
    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        if (attribute == null) {
            return ""
        }
        return attribute
    }
}