package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.navigation.Screen
import com.example.myapplication.presentation.screen.CatScreen
import com.example.myapplication.presentation.screen.FactMenuScreen
import com.example.myapplication.presentation.screen.MainScreen
import com.example.myapplication.presentation.screen.TarotScreen
import com.example.myapplication.presentation.screen.CatFactScreen
import com.example.myapplication.presentation.screen.TarotFactScreen
import com.example.myapplication.presentation.screen.SettingsScreen
import com.example.myapplication.presentation.screen.HoroscopeScreen
import com.example.myapplication.presentation.viewmodel.CatViewModel
import com.example.myapplication.presentation.viewmodel.SettingsViewModel
import com.example.myapplication.presentation.viewmodel.SettingsViewModelFactory
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.data.local.TarotDatabase
import com.example.myapplication.data.repository.TarotRepositoryImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val catViewModel: CatViewModel by viewModels()
    private val tarotViewModel: com.example.myapplication.presentation.viewmodel.TarotViewModel by viewModels {
        TarotViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel: SettingsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                factory = SettingsViewModelFactory(application)
            )
            val isDarkTheme by settingsViewModel.isDarkTheme.observeAsState(false)
            MyApplicationTheme(darkTheme = isDarkTheme) {
                AppNavigation(
                    catViewModel = catViewModel,
                    tarotViewModel = tarotViewModel,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { settingsViewModel.toggleTheme() },
                    app = application,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    catViewModel: CatViewModel,
    tarotViewModel: com.example.myapplication.presentation.viewmodel.TarotViewModel,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    app: android.app.Application,
    settingsViewModel: com.example.myapplication.presentation.viewmodel.SettingsViewModel
) {
    val navController = rememberNavController()
    val isButtonSoundEnabled by settingsViewModel.isButtonSoundEnabled.observeAsState(true)

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(
                onTarotClick = { navController.navigate(Screen.TarotScreen.route) },
                onHoroscopeClick = { navController.navigate(Screen.HoroscopeScreen.route) },
                onFactClick = { navController.navigate(Screen.FactMenuScreen.route) },
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,
                onSettingsClick = { navController.navigate(Screen.SettingsScreen.route) },
                isButtonSoundEnabled = isButtonSoundEnabled
            )
        }
        
        composable(Screen.TarotScreen.route) {
            TarotScreen(
                viewModel = tarotViewModel,
                onBackClick = { navController.navigateUp() },
                isDarkTheme = isDarkTheme,
                isButtonSoundEnabled = isButtonSoundEnabled
            )
        }

        composable(Screen.HoroscopeScreen.route) {
            HoroscopeScreen(
                onBackClick = { navController.navigateUp() },
                isDarkTheme = isDarkTheme
            )
        }

        composable(Screen.FactMenuScreen.route) {
            FactMenuScreen(
                onCatFactClick = { navController.navigate(Screen.CatFactScreen.route) },
                onTarotFactClick = { navController.navigate(Screen.TarotFactScreen.route) },
                onBackClick = { navController.navigateUp() },
                isDarkTheme = isDarkTheme,
                isButtonSoundEnabled = isButtonSoundEnabled
            )
        }

        composable(Screen.CatFactScreen.route) {
            CatFactScreen(
                onBackClick = { navController.navigateUp() },
                isDarkTheme = isDarkTheme,
                isButtonSoundEnabled = isButtonSoundEnabled
            )
        }

        composable(Screen.TarotFactScreen.route) {
            TarotFactScreen(
                onBackClick = { navController.navigateUp() },
                isDarkTheme = isDarkTheme,
                isButtonSoundEnabled = isButtonSoundEnabled
            )
        }

        composable(Screen.SettingsScreen.route) {
            SettingsScreen(
                onBackClick = { navController.navigateUp() },
                settingsViewModel = settingsViewModel
            )
        }
    }
}

class TarotViewModelFactory(
    private val application: android.app.Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.myapplication.presentation.viewmodel.TarotViewModel::class.java)) {
            val db = TarotDatabase.getInstance(application)
            val repo = TarotRepositoryImpl(db.tarotCardDao())
            @Suppress("UNCHECKED_CAST")
            return com.example.myapplication.presentation.viewmodel.TarotViewModel(application, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}