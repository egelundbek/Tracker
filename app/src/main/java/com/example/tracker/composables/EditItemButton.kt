package com.example.tracker.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*

@Composable
fun EditItemButton(
    onDelete: () -> Unit
) {
    MyDropdownMenu(
        icon = Icons.Default.Edit,
        contentDescription = "Edit",
        menuItems = listOf(
            "Slet" to onDelete
        )
    )
}