package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CatViewModel : ViewModel() {
    private val _currentCat = MutableStateFlow<String?>(null)
    val currentCat: StateFlow<String?> = _currentCat.asStateFlow()

    // Список имен файлов изображений котов
    private val cats = listOf(
        "cat1",
        "cat2",
        "cat3",
        "cat4",
        "cat5",
        "cat6",
        "cat7",
        "cat8",
        "cat9",
        "cat10"
    )

    fun getRandomCat() {
        _currentCat.value = cats.random()
    }
} 