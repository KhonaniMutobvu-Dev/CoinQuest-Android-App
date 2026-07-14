package com.group.coinquest

import androidx.room.*

@Dao
interface UserProgressDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: UserProgress)


    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    suspend fun getProgress(userId: Int): UserProgress?


    @Query("""
        UPDATE user_progress 
        SET xp = :xp, level = :level, streak = :streak, lastActiveDate = :date
        WHERE userId = :userId
    """)
    suspend fun updateProgress(
        userId: Int,
        xp: Int,
        level: Int,
        streak: Int,
        date: String
    )

}