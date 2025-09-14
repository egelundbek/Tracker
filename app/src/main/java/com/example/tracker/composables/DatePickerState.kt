package com.example.tracker.composables

import android.app.DatePickerDialog
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

class DatePickerState(
    val date: MutableState<String>,
    private val showDialog: () -> Unit
) {
    fun showDatePicker() = showDialog()
}

@Composable
fun rememberDatePicker(): DatePickerState {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val selectedDate = remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val isoDate = String.format(Locale.US,"%04d-%02d-%02d", year, month + 1, dayOfMonth)
            selectedDate.value = isoDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    return remember {
        DatePickerState(selectedDate) {
            datePickerDialog.show()
        }
    }
}
