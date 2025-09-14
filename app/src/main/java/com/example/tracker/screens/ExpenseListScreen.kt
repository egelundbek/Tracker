package com.example.tracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.tracker.composables.ExpenseCard
import com.example.tracker.data.AppDatabase
import com.example.tracker.data.entities.Expense
import com.example.tracker.util.EXPENSE_CATEGORIES
import com.example.tracker.util.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseListScreen(viewModel: SharedViewModel) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val expenseDao = db.expenseDao()
    val scope = rememberCoroutineScope()

    val expenses = remember { mutableStateListOf<Expense>() }

    val currentDate by viewModel.currentDate.collectAsState()
    val expenseFilter by remember { viewModel.listFilter }

    LaunchedEffect(currentDate, expenseFilter) {
        scope.launch {
            val loadedExpenses = withContext(Dispatchers.IO) {
                if (expenseFilter == "month") {
                    val yearMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"))
                    expenseDao.getExpensesByMonth(yearMonth)
                } else {
                    expenseDao.getAllExpensesNewestFirst()
                }
            }
            expenses.clear()
            expenses.addAll(loadedExpenses)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (expenses.isEmpty()) {
            item { Text("Ingen udgifter endnu!") }
        } else {
            items(expenses, key = { it.id }) { expense ->
                val category = EXPENSE_CATEGORIES.find { it.name == expense.category }

                ExpenseCard(
                    expense = expense,
                    backgroundColor = category?.color ?: MaterialTheme.colorScheme.surface,
                    onDelete = { exp ->
                        scope.launch {
                            expenseDao.delete(exp)
                            expenses.remove(exp)
                        }
                    }
                )
            }
        }
    }
}
