package com.lhuillier.examen_company_lhuillier.data.tier

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lhuillier.examen_company_lhuillier.data.model.Company

class DataConverter {

    @TypeConverter
    fun fromCompanyList(value: List<Company>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Company>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCompanyList(value: String): List<Company> {
        val gson = Gson()
        val type = object : TypeToken<List<Company>>() {}.type
        return gson.fromJson(value, type)
    }
}