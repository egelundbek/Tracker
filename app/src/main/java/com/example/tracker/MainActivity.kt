package com.example.tracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tracker.screens.ExpenseListScreen

import com.example.tracker.screens.HomeScreen
import com.example.tracker.screens.ExpenseScreen
import com.example.tracker.screens.IncomeListScreen
import com.example.tracker.screens.IncomeScreen
import com.example.tracker.composables.TopBar
import com.example.tracker.composables.BottomBar
import com.example.tracker.composables.MonthNavigator
import com.example.tracker.util.SharedViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExpenseTrackerApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerApp(viewModel: SharedViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val currentDate by viewModel.currentDate.collectAsState()

    val menuItems = when (currentRoute) {
        "home" -> listOf(
            when (viewModel.currentViewType.value) {
                "expenses" -> "Vis Indkomster" to { viewModel.toggleViewType() }
                "income" -> "Vis Udgifter" to { viewModel.toggleViewType() }
                else -> "Vis Indkomster" to { viewModel.toggleViewType() }
            },
            when (viewModel.currentChartType.value) {
                "pie" -> "Kategorier" to { viewModel.toggleChartType() }
                "grid" -> "Cirkeldiagram" to { viewModel.toggleChartType() }
                else -> "Kategorier" to { viewModel.toggleChartType() }
            }
        )
        "expenseList", "incomeList" -> listOf(
            when (viewModel.listFilter.value) {
                "month" -> "Alle" to { viewModel.toggleListFilter() }
                "all" -> "Måned" to { viewModel.toggleListFilter() }
                else -> "Alle" to { viewModel.toggleListFilter() }
            }
        )
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            TopBar(
                currentRoute = currentRoute,
                navController = navController,
                currentDate = currentDate,
                onMonthChange = { newDate -> viewModel.setMonth(newDate) },
                menuItems = menuItems,
                viewModel = viewModel
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(navController, viewModel) }
            composable("expense") { ExpenseScreen(navController) }
            composable("income") { IncomeScreen(navController) }
            composable("expenseList") { ExpenseListScreen(viewModel) }
            composable("incomeList") { IncomeListScreen(viewModel) }
        }
    }
}


