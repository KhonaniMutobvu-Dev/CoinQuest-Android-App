package com.group.coinquest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(

    @PrimaryKey
    val userId: Int,

    val xp: Int = 0,

    val level: Int = 1,

    val streak: Int = 0,

    val lastActiveDate: String = ""
)