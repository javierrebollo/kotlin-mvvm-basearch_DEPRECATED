package com.jrebollo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jrebollo.data.db.dao.UserDao
import com.jrebollo.data.db.entity.UserRoom

private const val DATABASE_NAME: String = "base-arch-db"
private const val DATABASE_VERSION: Int = 1

@Database(entities = [UserRoom::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(
            context: Context
        ): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(
                    context
                ).also { instance = it }
            }
        }

        private fun buildDatabase(
            context: Context
        ): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
