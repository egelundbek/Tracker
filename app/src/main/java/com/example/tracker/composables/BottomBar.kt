package com.example.tracker.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        actions = {
            MyDropdownMenu(
                icon = Icons.Default.Menu,
                contentDescription = "Menu",
                menuItems = listOf(
                    "Udgifter" to {
                        navController.navigate("expenseList")
                        { launchSingleTop = true }
                    },
                    "Indkomster" to {
                        navController.navigate("incomeList")
                        { launchSingleTop = true }
                    }
                )
            )
        },
        floatingActionButton = {
            MyDropdownMenu(
                icon = Icons.Default.Add,
                contentDescription = "Add",
                menuItems = listOf(
                    "Tilføj Udgift" to {
                        navController.navigate("expense")
                    },
                    "Tilføj Indkomst" to {
                        navController.navigate("income")
                    }
                )
            )
        }
    )
}
