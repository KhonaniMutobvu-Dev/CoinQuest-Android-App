package com.group.coinquest

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        Transaction::class,
        UserProgress::class,
        Achievement::class,
        Category::class,
        Budget::class
    ],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun achievementDao(): AchievementDao
    abstract fun categoryDao(): CategoryDao

    abstract fun budgetDao(): BudgetDao
}