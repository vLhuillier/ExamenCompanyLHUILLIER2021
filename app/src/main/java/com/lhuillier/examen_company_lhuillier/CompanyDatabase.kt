package com.lhuillier.examen_company_lhuillier

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lhuillier.examen_lhuillier.data.model.Company
import com.lhuillier.examen_lhuillier.data.tier.CompanyDAO

@Database(entities = arrayOf(Company::class), version = 1)
abstract class CompanyDatabase: RoomDatabase() {

    abstract fun dao(): CompanyDAO;

    companion object {
        private var INSTANCE: CompanyDatabase? = null
        @JvmStatic fun getDatabase(context: Context): CompanyDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                        .databaseBuilder(context, CompanyDatabase::class.java, "company.db")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE!!
        }
    }
}