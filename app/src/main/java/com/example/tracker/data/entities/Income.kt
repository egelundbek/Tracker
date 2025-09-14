package com.example.tracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val source: String,
    val category: String,
    val date: String
)
