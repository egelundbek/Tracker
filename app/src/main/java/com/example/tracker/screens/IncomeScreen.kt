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
import com.example.tracker.data.entities.Income
import com.example.tracker.util.INCOME_CATEGORIES
import com.example.tracker.composables.RadioButtons
import com.example.tracker.composables.SubmitButton
import com.example.tracker.composables.rememberDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun IncomeScreen(navController: NavHostController) {
    val datePickerState = rememberDatePicker()
    var total by remember { mutableStateOf("") }

    var selectedCategory by remember { mutableStateOf(INCOME_CATEGORIES[0]) }

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val incomeDao = db.incomeDao()
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
            value = total,
            onValueChange = { total = it },
            label = { Text("Beløb") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        RadioButtons(
            options = INCOME_CATEGORIES,
            selectedOption = selectedCategory,
            onOptionSelected = { selectedCategory = it },
            label = "Kategori"
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubmitButton(
            onSubmit = {
                if (total.isNotBlank() && datePickerState.date.value.isNotBlank()) {
                    val income = Income(
                        amount = total.toDoubleOrNull() ?: 0.0,
                        source = selectedCategory.name,
                        category = selectedCategory.name,
                        date = datePickerState.date.value
                    )

                    scope.launch(Dispatchers.IO) {
                        incomeDao.insert(income)
                    }

                    total = ""
                    true
                } else {
                    false
                }
            },
            successMessage = "Indkomst gemt",
            errorMessage = "Udfyld alle felter",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
