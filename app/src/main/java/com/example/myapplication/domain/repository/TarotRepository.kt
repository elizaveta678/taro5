package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.TarotCard
import kotlinx.coroutines.flow.Flow

interface TarotRepository {
    fun getAllCards(): Flow<List<TarotCard>>
    suspend fun insertCards(cards: List<TarotCard>)
    suspend fun getRandomCards(): List<TarotCard>
    suspend fun getAllCardsOnce(): List<TarotCard>
} 