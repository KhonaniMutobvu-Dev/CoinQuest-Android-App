package com.group.coinquest

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transactions")
data class Transaction(

    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    val userId:Int,
    val amount:Double,
    val description:String,
    val category:String,
    val date:String,
    val startTime:String,
    val endTime:String,
    val receipt:String?
)