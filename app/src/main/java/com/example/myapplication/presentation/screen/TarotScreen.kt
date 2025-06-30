package com.example.myapplication.presentation.screen

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.domain.model.TarotCard
import com.example.myapplication.presentation.viewmodel.TarotViewModel
import com.example.myapplication.ui.theme.LightBrown
import com.example.myapplication.ui.theme.DarkBrown

// Определяем бежевый цвет для кнопки и фона текста
private val BeigeColor = Color(0xFFFFF8E1)
private val TextColor = Color(0xFF4A2C06)
// Более темный бежевый для фона карт
private val CardBeigeColor = Color(0xFFFDECB5)
val buttonColor = BeigeColor
// Определяем фиксированные размеры для карт
private val CardWidth = 110.dp
private val CardAspectRatio = 0.6f // Соотношение сторон карты (ширина/высота)

private val ButtonShape = RoundedCornerShape(16.dp)

@Composable
fun TarotScreen(
    viewModel: TarotViewModel,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
    isButtonSoundEnabled: Boolean = true
) {
    val context = LocalContext.current
    val selectedCards by viewModel.selectedCards.collectAsState()
    val canGetPrediction by viewModel.canGetPrediction.collectAsState()

    // Получаем только доступность предсказания при входе на экран
    LaunchedEffect(Unit) {
        viewModel.checkPredictionAvailable()
    }

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
                    text = "Предсказание на день",
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
                // Отображение одной карты и предсказания
                val card = selectedCards.firstOrNull()
                if (card != null) {
                    TarotCardItem(card = card)
                    Spacer(modifier = Modifier.height(24.dp))
                }
                val buttonColor = BeigeColor
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        viewModel.getRandomPrediction()
                    },
                    enabled = card == null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = TextColor
                    ),
                    shape = ButtonShape,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(
                        text = "Новое предсказание",
                        color = TextColor,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
                if (!canGetPrediction && card != null) {
                    Text(
                        text = "Новое предсказание будет доступно завтра",
                        color = TextColor,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clip(ButtonShape)
                            .background(BeigeColor.copy(alpha = 0.9f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
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
                    containerColor = buttonColor,
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

@Composable
fun TarotCardItem(card: TarotCard) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(
        card.imageName,
        "drawable",
        context.packageName
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // карта
        Box(
            modifier = Modifier
                .width(CardWidth)
                .aspectRatio(CardAspectRatio)
                .clip(RoundedCornerShape(8.dp))
                .background(BeigeColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = card.description,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        // описание карты
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(BeigeColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = card.description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                textAlign = TextAlign.Center,
                color = TextColor,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
} 