package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TarotCardDao {
    @Query("SELECT * FROM tarot_cards")
    fun getAllCards(): Flow<List<TarotCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<TarotCardEntity>)

    @Query("SELECT * FROM tarot_cards ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCards(): List<TarotCardEntity>

    @Query("SELECT * FROM tarot_cards")
    suspend fun getAllCardsOnce(): List<TarotCardEntity>
} 