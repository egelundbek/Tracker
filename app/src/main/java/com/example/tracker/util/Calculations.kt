package com.example.tracker.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tracker.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

suspend fun calculateTotalExpenses(context: Context): Double = withContext(Dispatchers.IO) {
    val db = AppDatabase.getDatabase(context)
    db.expenseDao().getAllExpenses().sumOf { it.amount }
}

suspend fun calculateTotalSaved(context: Context): Double = withContext(Dispatchers.IO) {
    val db = AppDatabase.getDatabase(context)
    val totalExpenses = db.expenseDao().getAllExpenses().sumOf { it.amount }
    val totalIncome = db.incomeDao().getAllIncome().sumOf { it.amount }
    totalIncome - totalExpenses
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun calculateTotalExpensesByMonth(context: Context, date: LocalDate): Double = withContext(Dispatchers.IO) {
    val db = AppDatabase.getDatabase(context)
    val yearMonth = date.format(DateTimeFormatter.ofPattern("yyyy-MM"))
    db.expenseDao().getExpensesByMonth(yearMonth).sumOf { it.amount }
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun calculateTotalSavedByMonth(context: Context, date: LocalDate): Double = withContext(Dispatchers.IO) {
    val db = AppDatabase.getDatabase(context)
    val yearMonth = date.format(DateTimeFormatter.ofPattern("yyyy-MM"))

    val totalExpenses = db.expenseDao().getExpensesByMonth(yearMonth).sumOf { it.amount }
    val totalIncome = db.incomeDao().getIncomeByMonth(yearMonth).sumOf { it.amount }

    totalIncome - totalExpenses
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun getTotalIncomeByMonth(context: Context, date: LocalDate): Double = withContext(Dispatchers.IO) {
    val db = AppDatabase.getDatabase(context)
    val yearMonth = date.format(DateTimeFormatter.ofPattern("yyyy-MM"))
    db.incomeDao().getIncomeByMonth(yearMonth).sumOf { it.amount }
}
