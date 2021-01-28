package com.lhuillier.examen_company_lhuillier.data.tier

import androidx.room.*
import com.lhuillier.examen_company_lhuillier.data.model.Company
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistory
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistoryWithCompanies

@Dao
interface SearchHistoryWithCompaniesDAO {

    @Query("SELECT * FROM searchHistoryWithCompanies ")
    fun getAll(): List<SearchHistoryWithCompanies>?

    @Query("SELECT * FROM searchHistoryWithCompanies WHERE searchHistory=:id")
    fun getBySearchHistory(id: Long): List<SearchHistoryWithCompanies>

    @Query("SELECT * FROM searchHistoryWithCompanies INNER JOIN company ON searchHistoryWithCompanies.company = company.id WHERE searchHistory=:id")
    fun getCompaniesBySearchHistory(id: Long): List<Company>?


    @Query("SELECT * FROM searchHistoryWithCompanies WHERE company=:companyId")
    fun getByCompany(companyId: Long): List<SearchHistoryWithCompanies>?

    @Query("SELECT COUNT(*) FROM searchHistoryWithCompanies")
    fun count(): Int

    @Query("SELECT * FROM searchHistoryWithCompanies LIMIT 1 OFFSET :position")
    fun getByPosition(position: Int): SearchHistoryWithCompanies

    @Insert
    fun insert(searchHistoryWithCompanies: SearchHistoryWithCompanies): Long

    @Update
    fun update(searchHistoryWithCompanies: SearchHistoryWithCompanies)

    @Delete
    fun delete(searchHistoryWithCompanies: SearchHistoryWithCompanies)

}