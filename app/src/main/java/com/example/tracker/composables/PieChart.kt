package com.example.tracker.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.center
import kotlin.math.atan2

@Composable
fun PieChart(
    categoryTotals: Map<String, Double>,
    colors: Map<String, Color>
) {
    val total = categoryTotals.values.sum().takeIf { it > 0 } ?: 1.0
    var startAngles by remember { mutableStateOf(listOf<Float>()) }
    var sweepAngles by remember { mutableStateOf(listOf<Float>()) }
    var showLegend by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .pointerInput(true) {
                    detectTapGestures { offset ->
                        showLegend = true  // open popup

                        val center = size.center
                        val touchX = offset.x - center.x
                        val touchY = offset.y - center.y
                        var touchAngle = Math.toDegrees(atan2(touchY.toDouble(), touchX.toDouble())) + 90
                        if (touchAngle < 0) touchAngle += 360.0

                        selectedIndex = null
                        startAngles.forEachIndexed { index, start ->
                            val sweep = sweepAngles[index]
                            if (sweep > 0) {
                                var normalizedStart = start
                                var normalizedEnd = start + sweep

                                if (normalizedStart < 0) normalizedStart += 360
                                if (normalizedEnd > 360) {
                                    normalizedEnd -= 360
                                    if (touchAngle >= normalizedStart || touchAngle <= normalizedEnd) {
                                        selectedIndex = index
                                        return@forEachIndexed
                                    }
                                } else {
                                    if (touchAngle >= normalizedStart && touchAngle <= normalizedEnd) {
                                        selectedIndex = index
                                        return@forEachIndexed
                                    }
                                }
                            }
                        }
                    }
                }
        ) {
            var angle = -90f
            val tempStart = mutableListOf<Float>()
            val tempSweep = mutableListOf<Float>()

            categoryTotals.entries.forEach { (category, value) ->
                val sweepAngle = (value / total * 360).toFloat()
                if (value > 0) {
                    drawArc(
                        color = colors[category] ?: Color.Gray,
                        startAngle = angle,
                        sweepAngle = sweepAngle,
                        useCenter = true
                    )
                }
                tempStart.add(angle)
                tempSweep.add(sweepAngle)
                angle += sweepAngle
            }

            startAngles = tempStart
            sweepAngles = tempSweep
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedIndex?.let { index ->
            val categories = categoryTotals.keys.toList()
            val amounts = categoryTotals.values.toList()
            if (index < categories.size && index < amounts.size) {
                val category = categories[index]
                val amount = amounts[index]
                if (amount > 0) {
                    Text(text = "$category: $amount kr")
                }
            }
        }

        if (showLegend) {
            AlertDialog(
                onDismissRequest = { showLegend = false },
                title = { Text("Kategori") },
                text = {
                    Column {
                        categoryTotals.entries
                            .filter { it.value > 0.0 }
                            .forEach { entry ->
                                val category = entry.key
                                val amount = entry.value
                                val color = colors[category] ?: Color.Gray
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .background(color)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "$category: $amount kr")
                                }
                            }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showLegend = false }) {
                        Text("Luk")
                    }
                }
            )
        }
    }
}
