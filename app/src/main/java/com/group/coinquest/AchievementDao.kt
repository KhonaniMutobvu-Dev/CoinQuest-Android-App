package com.group.coinquest

import androidx.room.*

@Dao
interface AchievementDao {


    @Insert
    suspend fun insertAchievement(achievement: Achievement)


    @Query("SELECT * FROM achievements WHERE userId = :userId")
    suspend fun getUserAchievements(userId: Int): List<Achievement>


    @Query("UPDATE achievements SET isUnlocked = 1 WHERE id = :id")
    suspend fun unlockAchievement(id: Int)

}