package com.group.coinquest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface TransactionDao {


    @Insert
    suspend fun addTransaction(transaction: Transaction)


    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY id DESC")
    suspend fun getAll(userId: Int): List<Transaction>


    @Query("""
        SELECT * FROM transactions 
        WHERE userId = :userId 
        AND date BETWEEN :startDate AND :endDate
        ORDER BY id DESC
    """)
    suspend fun filterByDate(
        userId: Int,
        startDate: String,
        endDate: String
    ): List<Transaction>

}