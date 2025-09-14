package com.example.tracker.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tracker.data.AppDatabase
import com.example.tracker.data.entities.Expense
import com.example.tracker.composables.RadioButtons
import com.example.tracker.composables.SubmitButton
import com.example.tracker.composables.rememberDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.tracker.util.EXPENSE_CATEGORIES

@Composable
fun ExpenseScreen(navController: NavHostController) {
    val datePickerState = rememberDatePicker()
    var amount by remember { mutableStateOf("") }
    var ting by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(EXPENSE_CATEGORIES[0]) }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val expenseDao = db.expenseDao()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { datePickerState.showDatePicker() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = datePickerState.date.value.ifEmpty { "Dato" })
        }

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Beløb") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = ting,
            onValueChange = { ting = it },
            label = { Text("Ting") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        RadioButtons(
            options = EXPENSE_CATEGORIES,
            selectedOption = selectedCategory,
            onOptionSelected = { selectedCategory = it },
            label = "Kategori"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubmitButton(
            onSubmit = {
                if (amount.isNotBlank() && ting.isNotBlank() && datePickerState.date.value.isNotBlank()) {
                    val expense = Expense(
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        thing = ting,
                        category = selectedCategory.name,
                        date = datePickerState.date.value
                    )
                    scope.launch(Dispatchers.IO) {
                        try {
                            expenseDao.insert(expense)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    amount = ""
                    ting = ""
                    true
                } else {
                    false
                }
            },
            successMessage = "Udgift gemt",
            errorMessage = "Udfyld alle felter",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
