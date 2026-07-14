package com.group.coinquest

import androidx.room.*

@Dao
interface BudgetDao {

    @Insert
    suspend fun insert(budget: Budget)

    @Query("SELECT * FROM budgets WHERE userId = :userId")
    suspend fun getAll(userId: Int): List<Budget>

    @Delete
    suspend fun delete(budget: Budget)
}