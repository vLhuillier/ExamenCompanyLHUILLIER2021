package com.lhuillier.examen_company_lhuillier.data.tier

import androidx.room.*
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistory

@Dao
interface SearchHistoryDAO {

    @Query("SELECT * FROM searchHistory ORDER BY date")
    fun getAll(): List<SearchHistory>?

    @Query("SELECT * FROM searchHistory WHERE id=:id")
    fun getById(id: Long): SearchHistory?

    @Query("SELECT * FROM searchHistory WHERE word=:word")
    fun getBySearchWord(word: String): SearchHistory?

    @Query("SELECT COUNT(*) FROM searchHistory")
    fun count(): Int

    @Query("SELECT * FROM searchHistory ORDER BY date LIMIT 1 OFFSET :position")
    fun getByPosition(position: Int): SearchHistory

    @Insert
    fun insert(searchhistory: SearchHistory): Long

    @Update
    fun update(searchhistory: SearchHistory)

    @Delete
    fun delete(searchhistory: SearchHistory)

}