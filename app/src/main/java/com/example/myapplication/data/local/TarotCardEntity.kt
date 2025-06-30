package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarot_cards")
data class TarotCardEntity(
    @PrimaryKey
    val imageName: String,
    val description: String
) 