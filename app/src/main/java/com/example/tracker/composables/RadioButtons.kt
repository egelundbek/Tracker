package com.example.tracker.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tracker.util.Category

@Composable
fun RadioButtons(
    options: List<Category>,
    selectedOption: Category,
    onOptionSelected: (Category) -> Unit,
    label: String? = null
) {
    Column {
        label?.let {
            Text(it, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) }
                    )
                    .padding(4.dp)
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = option.color
                    )
                )
                Text(
                    text = option.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = option.color,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
