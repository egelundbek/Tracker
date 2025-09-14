package com.example.tracker.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class SharedViewModel : ViewModel() {

    private val _currentDate = MutableStateFlow(LocalDate.now())
    private val _currentViewType = mutableStateOf("expenses")
    private val _currentChartType = mutableStateOf("pie")

    private val _listFilter = mutableStateOf("all")

    val currentDate: StateFlow<LocalDate> = _currentDate
    val currentViewType: MutableState<String> get() = _currentViewType
    val currentChartType: MutableState<String> get() = _currentChartType

    val listFilter: MutableState<String> get() = _listFilter

    fun setMonth(newDate: LocalDate) {
        _currentDate.value = newDate
    }

    fun toggleViewType() {
        _currentViewType.value =
            if (_currentViewType.value == "expenses") "income" else "expenses"
    }

    fun toggleChartType() {
        _currentChartType.value =
            if (_currentChartType.value == "pie") "grid" else "pie"
    }

    fun toggleListFilter() {
        _listFilter.value =
            if (_listFilter.value == "month") "all" else "month"
    }

}
