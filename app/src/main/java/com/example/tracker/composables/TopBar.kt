package com.example.tracker.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tracker.util.SharedViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    currentRoute: String?,
    navController: NavController,
    viewModel: SharedViewModel,
    currentDate: LocalDate,
    onMonthChange: (LocalDate) -> Unit,
    menuItems: List<Pair<String, () -> Unit>> = emptyList()
) {
    CenterAlignedTopAppBar(
        title = {
            when (currentRoute) {
                "home" -> MonthNavigator(
                    currentDate = currentDate,
                    onMonthChange = onMonthChange
                )
                "expense" -> Text("Tilføj Udgift")
                "income" -> Text("Tilføj Indkomst")
                "expenseList" -> {
                    if (viewModel.listFilter.value == "month") {
                        MonthNavigator(currentDate = currentDate, onMonthChange = onMonthChange)
                    } else {
                        Text("Alle Udgifter")
                    }
                }
                "incomeList" -> {
                    if (viewModel.listFilter.value == "month") {
                        MonthNavigator(currentDate = currentDate, onMonthChange = onMonthChange)
                    } else {
                        Text("Alle Indkomster")
                    }
                }
                else -> Text("Tracker")
            }
        },
        navigationIcon = {
            if (currentRoute != "home") {
                IconButton(
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            }
        },
        actions = {
            if (menuItems.isNotEmpty()) {
                MyDropdownMenu(
                    icon = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    menuItems = menuItems
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
