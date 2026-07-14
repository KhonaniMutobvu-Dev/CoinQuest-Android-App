package com.group.coinquest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,
    val categoryName: String,
    val minGoal: Double,
    val maxGoal: Double
)