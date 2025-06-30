package com.example.myapplication.presentation.navigation

sealed class Screen(val route: String) {
    object TarotScreen : Screen("tarot_screen")
    object HoroscopeScreen : Screen("horoscope_screen")
    object FactMenuScreen : Screen("fact_menu_screen")
    object CatFactScreen : Screen("cat_fact_screen")
    object TarotFactScreen : Screen("tarot_fact_screen")
    object SettingsScreen : Screen("settings_screen")
} 