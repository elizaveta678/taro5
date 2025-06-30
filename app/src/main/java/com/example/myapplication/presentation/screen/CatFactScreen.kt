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

private val catFacts = listOf(
    "Коты спят 70% своей жизни – примерно 16 часов в день. Мечта, да?",
    "Усы помогают котам ориентироваться – если обрезать вибриссы, кот может потеряться даже в знакомом месте.",
    "Кошки не чувствуют сладкого вкуса – зато обожают жир и мясо!",
    "Мурлыканье лечит – вибрации 25-150 Гц ускоряют заживление костей и мышц.",
    "Коты трутся о людей, чтобы 'пометить' их – это значит: 'Ты мой человеческий!'",
    "В Древнем Египте за убийство кота казнили – их считали священными животными.",
    "Коты могут издавать до 100 разных звуков – а собаки только около 10.",
    "Сердце кошки бьется в 2 раза быстрее человеческого – около 140-220 ударов в минуту!",
    "Коты пьют воду, не замочив подбородок – они загибают язык назад, создавая 'ковшик'.",
    "В Японии есть 'кошачий остров' (Тасиродзима) – там живет больше котов, чем людей!"
)

private val BeigeColor = Color(0xFFFFF8E1)
private val TextColor = Color(0xFF4A2C06)
private val TitleColor = BeigeColor
private val ButtonShape = RoundedCornerShape(16.dp)

@Composable
fun CatFactScreen(onBackClick: () -> Unit, isDarkTheme: Boolean, isButtonSoundEnabled: Boolean = true) {
    var fact by remember { mutableStateOf(catFacts.random()) }
    val buttonColor = BeigeColor
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
                    text = "Кото-Факт",
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
                Button(
                    onClick = {
                        if (isButtonSoundEnabled) {
                            val mediaPlayer = MediaPlayer.create(context, R.raw.mua)
                            mediaPlayer.setVolume(1f, 1f)
                            mediaPlayer.setOnCompletionListener { it.release() }
                            mediaPlayer.start()
                        }
                        fact = catFacts.random()
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