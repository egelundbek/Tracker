package com.example.tracker.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tracker.composables.CategoryGrid
import com.example.tracker.composables.MoneyCard
import com.example.tracker.composables.PieChart
import com.example.tracker.data.AppDatabase
import com.example.tracker.util.*
import kotlinx.coroutines.Dispatchers
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: SharedViewModel) {
    val context = LocalContext.current

    var totalSpent by remember { mutableDoubleStateOf(0.0) }
    var totalSaved by remember { mutableDoubleStateOf(0.0) }
    var categoryTotals by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }
    var categoryColors by remember { mutableStateOf<Map<String, androidx.compose.ui.graphics.Color>>(emptyMap()) }

    val currentDate by viewModel.currentDate.collectAsState()
    val currentViewType by remember { viewModel.currentViewType }
    val currentChartType by remember { viewModel.currentChartType }

    LaunchedEffect(currentDate, currentViewType) {
        val yearMonth = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val db = AppDatabase.getDatabase(context)

        totalSpent = calculateTotalExpensesByMonth(context, currentDate)
        totalSaved = calculateTotalSavedByMonth(context, currentDate)

        if (currentViewType == "expenses") {
            val loadedExpenses = withContext(Dispatchers.IO)
            { db.expenseDao().getExpensesByMonth(yearMonth) }
            val (totals, colors) = calculateCategoryTotals(
                loadedExpenses,
                EXPENSE_CATEGORIES,
                { it.category },
                { it.amount }
            )
            categoryTotals = totals
            categoryColors = colors
        } else {
            val loadedIncomes = withContext(Dispatchers.IO)
            { db.incomeDao().getIncomeByMonth(yearMonth) }
            val (totals, colors) = calculateCategoryTotals(
                loadedIncomes,
                INCOME_CATEGORIES,
                { it.category },
                { it.amount }
            )
            categoryTotals = totals
            categoryColors = colors
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        MoneyCard(
            label = if (currentViewType == "expenses") "Brugt" else "Tjent",
            amount = if (currentViewType == "expenses") totalSpent else totalSaved,
            backgroundColor = if (currentViewType == "expenses") BRUGT else TJENT
        )

        Spacer(modifier = Modifier.height(16.dp))

        MoneyCard(
            label = "Tilbage",
            amount = totalSaved,
            backgroundColor = TILBAGE
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (currentChartType == "pie") {
            PieChart(
                categoryTotals = categoryTotals,
                colors = categoryColors
            )
        } else {
            CategoryGrid(
                categoryTotals = categoryTotals,
                colors = categoryColors
            )
        }
    }
}
