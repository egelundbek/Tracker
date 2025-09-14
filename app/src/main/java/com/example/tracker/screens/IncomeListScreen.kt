package com.example.tracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tracker.composables.IncomeCard
import com.example.tracker.data.AppDatabase
import com.example.tracker.data.entities.Income
import com.example.tracker.util.INCOME_CATEGORIES
import com.example.tracker.util.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IncomeListScreen(viewModel: SharedViewModel) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val incomeDao = db.incomeDao()
    val scope = rememberCoroutineScope()

    val currentDate by viewModel.currentDate.collectAsState()
    val listFilter by viewModel.listFilter

    var incomes by remember { mutableStateOf(listOf<Income>()) }

    LaunchedEffect(currentDate, listFilter) {
        scope.launch {
            incomes = withContext(Dispatchers.IO) {
                if (listFilter == "month") {
                    val yearMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"))
                    incomeDao.getIncomeByMonth(yearMonth)
                } else {
                    incomeDao.getAllIncome()
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (incomes.isEmpty()) {
            item { Text("Ingen indkomst endnu!") }
        } else {
            items(incomes, key = { it.id }) { income ->
                val category = INCOME_CATEGORIES.find { it.name == income.category }

                IncomeCard(
                    income = income,
                    backgroundColor = category?.color ?: MaterialTheme.colorScheme.surface,
                    onDelete = { toDelete ->
                        scope.launch {
                            withContext(Dispatchers.IO) { incomeDao.delete(toDelete) }
                            incomes = incomes.filterNot { it.id == toDelete.id }
                        }
                    }
                )
            }
        }
    }
}
