package com.group.coinquest

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: Int,

    val name: String
)