package com.example.myapplication.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import kotlin.random.Random
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import com.example.myapplication.ui.theme.LightBrown
import com.example.myapplication.ui.theme.DarkBrown
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext

private val tarotFacts = listOf(
    "Карты Таро изначально были игрой – в Европе XV века в них играли, как в бридж.",
    "'Дурак' (Шут) – единственная карта без номера – он символизирует начало и невинность.",
    "В колоде Райдера-Уэйта (самой популярной) есть скрытые коты – например, на карте 'Королева пентаклей' у ног женщины сидит черный кот.",
    "Карта 'Луна' часто связана с кошками – потому что они ночные животные и символ тайн.",
    "В Средневековье Таро считали 'дьявольскими картами' – но на самом деле их использовали для философских размышлений.",
    "'Маг' похож на фокусника – но на самом деле он управляет стихиями, а не просто жонглирует.",
    "'Башня' – самая пугающая карта – но она означает не катастрофу, а разрушение иллюзий.",
    "Карта 'Солнце' – самая позитивная – она почти всегда означает радость и успех.",
    "В некоторых колодах вместо 'Папы' и 'Папессы' есть 'Жрец' и 'Верховная жрица' – чтобы избежать религиозных споров.",
    "Коты в Таро – символ интуиции – поэтому их часто изображают рядом с 'Волшебницей' или 'Отшельником'."
)

private val BeigeColor = Color(0xFFFFF8E1)
private val TextColor = Color(0xFF4A2C06)
private val TitleColor = BeigeColor
private val ButtonShape = RoundedCornerShape(16.dp)

@Composable
fun TarotFactScreen(onBackClick: () -> Unit, isDarkTheme: Boolean, isButtonSoundEnabled: Boolean = true) {
    var fact by remember { mutableStateOf(tarotFacts.random()) }
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
                    text = "Таро-Факт",
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
                Text(
                    text = fact,
                    fontSize = 20.sp,
                    color = TextColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(BeigeColor.copy(alpha = 0.9f), RoundedCornerShape(12.dp))
                        .padding(24.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                val buttonColor = BeigeColor
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        fact = tarotFacts.random()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = TextColor
                    ),
                    shape = ButtonShape,
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Еще факт", color = TextColor, textAlign = TextAlign.Center)
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
                Text("Назад", color = TextColor, textAlign = TextAlign.Center)
            }
        }
    }
} 