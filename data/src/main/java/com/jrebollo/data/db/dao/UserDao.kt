package com.jrebollo.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jrebollo.data.db.entity.UserRoom

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY name")
    fun getAllUsers(): LiveData<List<UserRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userList: List<UserRoom>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserRoom): Long
}