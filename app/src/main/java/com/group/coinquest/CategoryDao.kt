package com.group.coinquest

import androidx.room.*

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: Category)

    @Query("SELECT * FROM categories WHERE userId = :userId ORDER BY id DESC")
    suspend fun getAll(userId: Int): List<Category>

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM categories WHERE userId = :userId AND name = :name LIMIT 1")
    suspend fun findByName(userId: Int, name: String): Category?
}
