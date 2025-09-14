package com.example.tracker.data.dao

import androidx.room.*
import com.example.tracker.data.entities.Income

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: Income)

    @Update
    suspend fun update(income: Income)

    @Delete
    suspend fun delete(income: Income)

    @Query("SELECT * FROM income ORDER BY date DESC")
    suspend fun getAllIncome(): List<Income>

    @Query("SELECT * FROM income WHERE category = :category ORDER BY date DESC")
    suspend fun getIncomeByCategory(category: String): List<Income>

    @Query("DELETE FROM income")
    suspend fun deleteAll()

    @Query("SELECT * FROM income WHERE strftime('%Y-%m', date) = :yearMonth ORDER BY date DESC")
    suspend fun getIncomeByMonth(yearMonth: String): List<Income>

}