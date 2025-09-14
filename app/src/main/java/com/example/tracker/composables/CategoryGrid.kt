package com.example.tracker.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryGrid(
    categoryTotals: Map<String, Double>,
    colors: Map<String, Color>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        categoryTotals.toList()
            .sortedByDescending { it.second }
            .chunked(2)
            .forEach { rowCategories ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowCategories.forEach { (category, total) ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = colors[category] ?: Color.LightGray
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(category, style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("DKK ${"%.2f".format(total)}", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }

                    if (rowCategories.size < 2) Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
    }
}
