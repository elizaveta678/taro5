package com.example.myapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.SettingsRepository

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = SettingsRepository(app)
    private val _isDarkTheme = MutableLiveData(repo.isDarkTheme())
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    private val _isButtonSoundEnabled = MutableLiveData(repo.isButtonSoundEnabled())
    val isButtonSoundEnabled: LiveData<Boolean> = _isButtonSoundEnabled

    fun toggleTheme() {
        val newValue = !(_isDarkTheme.value ?: false)
        repo.setDarkTheme(newValue)
        _isDarkTheme.value = newValue
    }

    fun toggleButtonSound() {
        val newValue = !(_isButtonSoundEnabled.value ?: true)
        repo.setButtonSoundEnabled(newValue)
        _isButtonSoundEnabled.value = newValue
    }
} 