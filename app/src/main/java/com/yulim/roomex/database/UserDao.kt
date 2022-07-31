package com.yulim.roomex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM UserDto")
    fun getAll(): List<UserDto>

    @Insert
    fun insert(user: UserDto)

    @Query("DELETE FROM UserDto WHERE name = :name")
    fun deleteUserByName(name: String)
}