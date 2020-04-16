package com.jrebollo.basearch.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jrebollo.basearch.data.db.entity.UserRoom

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<UserRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserRoom>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: UserRoom): Long
}