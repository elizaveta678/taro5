package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences

class SettingsRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    fun isDarkTheme(): Boolean = prefs.getBoolean(KEY_DARK_THEME, false)
    fun setDarkTheme(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_THEME, enabled).apply()
    }

    fun isButtonSoundEnabled(): Boolean = prefs.getBoolean(KEY_BUTTON_SOUND, true)
    fun setButtonSoundEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_BUTTON_SOUND, enabled).apply()
    }

    companion object {
        private const val KEY_DARK_THEME = "dark_theme"
        private const val KEY_BUTTON_SOUND = "button_sound"
    }
} 