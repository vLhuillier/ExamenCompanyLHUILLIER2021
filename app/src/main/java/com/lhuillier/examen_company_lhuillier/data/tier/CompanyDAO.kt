package com.lhuillier.examen_lhuillier.data.tier

import androidx.room.*
import com.lhuillier.examen_lhuillier.data.model.Company

@Dao
interface CompanyDAO {

    @Query("SELECT * FROM company ORDER BY l1_normalisee")
    fun getAll(): List<Company>

    @Query("SELECT * FROM company WHERE id=:id")
    fun getById(id: Long): Company?

    @Query("SELECT COUNT(*) FROM company")
    fun count(): Int

    @Query("SELECT * FROM company ORDER BY l1_normalisee LIMIT 1 OFFSET :position")
    fun getByPosition(position: Int): Company?

    @Insert
    fun insert(company: Company): Long

    @Update
    fun update(company: Company)

    @Delete
    fun delete(company: Company)
}