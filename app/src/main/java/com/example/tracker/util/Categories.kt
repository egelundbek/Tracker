package com.example.tracker.util

import androidx.compose.ui.graphics.Color

data class Category(val name: String, val color: Color)

val EXPENSE_CATEGORIES = listOf(
    Category("Dagligvarer", Color(0xFF715660)),
    Category("Transport", Color(0xFF846470)),
    Category("Underholdning", Color(0xFF566071)),
    Category("Shopping", Color(0xFF879e82)),
    Category("Mad og Drikke", Color(0xFFc39f72)),
    Category("Abonnementer", Color(0xFFd5bf95)),
    Category("Faste Udgifter", Color(0xFF727f96)),
    Category("Andet", Color(0xFF667762))
)

val INCOME_CATEGORIES = listOf(
    Category("Løn", Color(0xFF8B7355)),
    Category("Gaver", Color(0xFF6B8E73)),
    Category("Andet", Color(0xFF7A6B85))
)

val TILBAGE = Color(0xFF879e82)
val BRUGT = Color(0xFFc39f72)
val TJENT = Color(0xFF6B8E73)

fun <T> calculateCategoryTotals(
    items: List<T>,
    categories: List<Category>,
    getCategory: (T) -> String,
    getAmount: (T) -> Double
    ):
        Pair<Map<String, Double>, Map<String, Color>> {
        val totals = items.groupBy(getCategory)
            .mapValues { it.value.sumOf(getAmount)
            }

        val totalsMap = categories.associate { it.name to (totals[it.name] ?: 0.0) }
        val colorsMap = categories.associate { it.name to it.color }
        return totalsMap to colorsMap
    }
