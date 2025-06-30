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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext

private val BeigeColor = Color(0xFFFFF8E1)
private val TextColor = Color(0xFF4A2C06)
private val TitleColor = BeigeColor
private val ButtonShape = RoundedCornerShape(16.dp)

@Composable
fun MainScreen(
    onTarotClick: () -> Unit,
    onHoroscopeClick: () -> Unit,
    onFactClick: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onSettingsClick: () -> Unit,
    isButtonSoundEnabled: Boolean = true
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        // фоновое изображение
        val bgRes = if (isDarkTheme) R.drawable.fon2 else R.drawable.fon
        Image(
            painter = painterResource(id = bgRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // кнопка Настройки в правом верхнем углу
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Button(
                onClick = {
                    if (isButtonSoundEnabled) {
                        val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                        mediaPlayer.setVolume(1f, 1f)
                        mediaPlayer.setOnCompletionListener { it.release() }
                        mediaPlayer.start()
                    }
                    onSettingsClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BeigeColor,
                    contentColor = TextColor
                ),
                shape = ButtonShape,
                modifier = Modifier.width(140.dp)
            ) {
                Text(
                    text = "Настройки",
                    color = TextColor,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Основной контент
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Название приложения
                Text(
                    text = "МЯУкающие карты",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = TextColor,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .clip(ButtonShape)
                        .background(TitleColor.copy(alpha = 0.9f))
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка Таро
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        onTarotClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BeigeColor,
                        contentColor = TextColor
                    ),
                    shape = ButtonShape,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Предсказание на день",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextColor,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Кнопка Гороскоп на сегодня
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        onHoroscopeClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BeigeColor,
                        contentColor = TextColor
                    ),
                    shape = ButtonShape,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Гороскоп на сегодня",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextColor,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // кнопка Факт дня
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        onFactClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BeigeColor,
                        contentColor = Color.Black
                    ),
                    shape = ButtonShape,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Фактики",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextColor,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        // Кнопка выхода внизу экрана
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    if (isButtonSoundEnabled) {
                        val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                        mediaPlayer.setVolume(1f, 1f)
                        mediaPlayer.setOnCompletionListener { it.release() }
                        mediaPlayer.start()
                    }
                    android.os.Process.killProcess(android.os.Process.myPid())
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BeigeColor,
                    contentColor = TextColor
                ),
                shape = ButtonShape,
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "Выход",
                    color = TextColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
} 