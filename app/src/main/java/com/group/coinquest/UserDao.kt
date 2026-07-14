package com.group.coinquest

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert

@Dao
interface UserDao {


    @Insert
    suspend fun registerUser(user: User)


    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(
        username:String,
        password:String
    ): User?


    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun checkUser(
        username:String
    ): User?

}