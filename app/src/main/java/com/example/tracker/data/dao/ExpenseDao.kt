package com.example.tracker.data.dao

import androidx.room.*
import com.example.tracker.data.entities.Expense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    suspend fun getExpensesByCategory(category: String): List<Expense>

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    suspend fun getAllExpensesNewestFirst(): List<Expense>

    @Query("SELECT * FROM expenses WHERE strftime('%Y-%m', date) = :yearMonth ORDER BY date DESC")
    suspend fun getExpensesByMonth(yearMonth: String): List<Expense>

    @Query("DELETE FROM expenses")
    suspend fun deleteAll()

    @Query("DELETE FROM expenses WHERE category = :category")
    suspend fun deleteByCategory(category: String)

}
