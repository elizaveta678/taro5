package com.example.myapplication.data.repository

import com.example.myapplication.data.local.TarotCardDao
import com.example.myapplication.data.local.TarotCardEntity
import com.example.myapplication.domain.model.TarotCard
import com.example.myapplication.domain.repository.TarotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//ассинхронная работа  с данными (функции)
class TarotRepositoryImpl(
    private val dao: TarotCardDao
) : TarotRepository {

    override fun getAllCards(): Flow<List<TarotCard>> {
        return dao.getAllCards().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertCards(cards: List<TarotCard>) {
        dao.insertCards(cards.map { it.toEntity() })
    }

    override suspend fun getRandomCards(): List<TarotCard> {
        return dao.getRandomCards().map { it.toDomain() }
    }

    override suspend fun getAllCardsOnce(): List<TarotCard> {
        return dao.getAllCardsOnce().map { it.toDomain() }
    }

    private fun TarotCardEntity.toDomain(): TarotCard {
        return TarotCard(
            imageName = imageName,
            description = description
        )
    }

    private fun TarotCard.toEntity(): TarotCardEntity {
        return TarotCardEntity(
            imageName = imageName,
            description = description
        )
    }
} 