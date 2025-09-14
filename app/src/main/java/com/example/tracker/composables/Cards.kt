package com.example.tracker.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tracker.data.entities.Expense
import com.example.tracker.data.entities.Income

@Composable
fun ExpenseCard(
    expense: Expense,
    backgroundColor: Color,
    onDelete: (Expense) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Dato: ${expense.date}")
                    Text("Beløb: DKK ${"%.2f".format(expense.amount)}")
                    Text("Ting: ${expense.thing}")
                    Text("Kategori: ${expense.category}")
                }

                EditItemButton {
                    onDelete(expense)
                }
            }
        }
    }
}

@Composable
fun IncomeCard(
    income: Income,
    backgroundColor: Color,
    onDelete: (Income) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Dato: ${income.date}")
                    Text("Beløb: DKK ${"%.2f".format(income.amount)}")
                    Text("Kilde: ${income.category}")
                }

                EditItemButton {
                    onDelete(income)
                }
            }
        }
    }
}

@Composable
fun MoneyCard(
    label: String,
    amount: Double,
    backgroundColor: Color
) {
    Card(modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "DKK ${"%.2f".format(amount)}",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}



