package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "horoscope_cache")
data class HoroscopeCacheEntity(
    @PrimaryKey val key: String, // zodiacIndex|date
    val zodiacIndex: Int,
    val date: String, // "yyyy-MM-dd"
    val text: String,
    val timestamp: Long
) 