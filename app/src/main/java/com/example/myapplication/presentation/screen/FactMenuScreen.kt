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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.example.myapplication.ui.theme.LightBrown
import com.example.myapplication.ui.theme.DarkBrown
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
fun FactMenuScreen(
    onCatFactClick: () -> Unit,
    onTarotFactClick: () -> Unit,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    isButtonSoundEnabled: Boolean = true
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        val bgRes = if (isDarkTheme) R.drawable.fon2 else R.drawable.fon
        Image(
            painter = painterResource(id = bgRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
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
                Text(
                    text = "Факт дня",
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
                val buttonColor = BeigeColor
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        onCatFactClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = TextColor
                    ),
                    shape = ButtonShape,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Кото-Факт",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextColor,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        onTarotFactClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = TextColor
                    ),
                    shape = ButtonShape,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Таро-Факт",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextColor,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        // кнопка 'назад' внизу экрана
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
                    onBackClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BeigeColor,
                    contentColor = TextColor
                ),
                shape = ButtonShape,
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "Назад",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
} 