package com.example.myapplication.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.myapplication.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.presentation.viewmodel.SettingsViewModel
import androidx.compose.runtime.livedata.observeAsState
import android.app.Application
import androidx.compose.runtime.getValue
import com.example.myapplication.presentation.viewmodel.SettingsViewModelFactory

private val BeigeColor = Color(0xFFFFF8E1)
private val TextColor = Color(0xFF4A2C06)
private val ButtonShape = RoundedCornerShape(16.dp)

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    settingsViewModel: com.example.myapplication.presentation.viewmodel.SettingsViewModel
) {
    val isDarkTheme by settingsViewModel.isDarkTheme.observeAsState(false)
    val isButtonSoundEnabled by settingsViewModel.isButtonSoundEnabled.observeAsState(true)
    Box(modifier = Modifier.fillMaxSize()) {
        val bgRes = if (isDarkTheme) R.drawable.fon2 else R.drawable.fon
        Image(
            painter = painterResource(id = bgRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Настройки",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = TextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .clip(ButtonShape)
                    .background(BeigeColor.copy(alpha = 0.9f))
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = if (isDarkTheme) "Тёмная тема" else "Светлая тема",
                    color = if (isDarkTheme) Color.White else TextColor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { settingsViewModel.toggleTheme() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = TextColor,
                        uncheckedThumbColor = TextColor,
                        checkedTrackColor = BeigeColor,
                        uncheckedTrackColor = BeigeColor
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = if (isButtonSoundEnabled) "Звук кнопок: вкл" else "Звук кнопок: выкл",
                    color = if (isDarkTheme) Color.White else TextColor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Switch(
                    checked = isButtonSoundEnabled,
                    onCheckedChange = { settingsViewModel.toggleButtonSound() },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = TextColor,
                        uncheckedThumbColor = TextColor,
                        checkedTrackColor = BeigeColor,
                        uncheckedTrackColor = BeigeColor
                    )
                )
            }
        }
        // Кнопка 'Назад' внизу экрана
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BeigeColor,
                    contentColor = TextColor
                ),
                shape = ButtonShape,
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "Назад",
                    color = TextColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
} 