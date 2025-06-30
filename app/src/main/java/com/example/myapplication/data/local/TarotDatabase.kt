package com.example.myapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.HoroscopeCacheEntity
import com.example.myapplication.data.local.HoroscopeCacheDao

@Database(entities = [TarotCardEntity::class, HoroscopeCacheEntity::class], version = 2)
abstract class TarotDatabase : RoomDatabase() {
    abstract fun tarotCardDao(): TarotCardDao
    abstract fun horoscopeCacheDao(): HoroscopeCacheDao

    companion object {
        @Volatile
        private var INSTANCE: TarotDatabase? = null

        fun getInstance(context: Context): TarotDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarotDatabase::class.java,
                    "tarot_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 