package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HoroscopeCacheDao {
    @Query("SELECT * FROM horoscope_cache WHERE key = :key LIMIT 1")
    suspend fun getCache(key: String): HoroscopeCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(entity: HoroscopeCacheEntity)
} 