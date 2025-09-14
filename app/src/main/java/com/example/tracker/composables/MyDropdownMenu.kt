package com.example.tracker.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyDropdownMenu(
    icon: ImageVector,
    contentDescription: String,
    menuItems: List<Pair<String, () -> Unit>>
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(icon, contentDescription = contentDescription)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEach { (label, action) ->
                MenuItem(label = label) {
                    expanded = false
                    action()
                }
            }
        }
    }
}
