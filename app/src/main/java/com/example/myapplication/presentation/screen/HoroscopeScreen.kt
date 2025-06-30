package com.example.myapplication.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import coil.compose.AsyncImage
import com.example.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*
import com.example.myapplication.data.local.TarotDatabase
import com.example.myapplication.data.local.HoroscopeCacheEntity
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape

// осн цвета и формы для UI
private val BeigeColor = Color(0xFFFFF8E1) // цвет фона карточек
private val TextColor = Color(0xFF4A2C06)  // цвет основного текста
private val ButtonShape = RoundedCornerShape(16.dp) // скругление для кнопок и карточек

// список зз (отображаемые имена)
private val zodiacList = listOf(
    "Овен", "Телец", "Близнецы", "Рак", "Лев", "Дева",
    "Весы", "Скорпион", "Стрелец", "Козерог", "Водолей", "Рыбы"
)
// список ресурсов иконок для каждого зз
private val zodiacIcons = listOf(
    R.drawable.ic_aries, R.drawable.ic_taurus, R.drawable.ic_gemini, R.drawable.ic_cancer,
    R.drawable.ic_leo, R.drawable.ic_virgo, R.drawable.ic_libra, R.drawable.ic_scorpio,
    R.drawable.ic_sagittarius, R.drawable.ic_capricorn, R.drawable.ic_aquarius, R.drawable.ic_pisces
)

/**
 * основной экран гороскопа на день.
 * позволяет выбрать знак зодиака и получить предсказание с сайта 1001goroskop.ru.
 * @param onBackClick - обработчик возврата назад
 * @param isDarkTheme - флаг для смены фона
 */
@Composable
fun HoroscopeScreen(
    onBackClick: () -> Unit,
    isDarkTheme: Boolean
) {
    val context = LocalContext.current
    var selectedZodiac by remember { mutableStateOf<Int?>(null) } // Индекс выбранного знака

    // Сброс выбранного знака при первом запуске экрана
    LaunchedEffect(Unit) {
        if (selectedZodiac != null) {
            selectedZodiac = null
        }
    }

    var horoscopeText by remember { mutableStateOf("") } // Текст гороскопа
    var isLoading by remember { mutableStateOf(false) }   // Флаг загрузки
    var error by remember { mutableStateOf<String?>(null) } // Сообщение об ошибке

    // Выбор фона в зависимости от темы
    val bgRes = if (isDarkTheme) R.drawable.fon2 else R.drawable.fon
    Box(modifier = Modifier.fillMaxSize()) {
        // Фоновое изображение
        Image(
            painter = painterResource(id = bgRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        // Основная колонка с контентом
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Заголовок экрана
            Text(
                text = "Гороскоп на сегодня",
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
            // Если знак не выбран, показываем сетку знаков
            if (selectedZodiac == null) {
                ZodiacGrid(onSelect = { selectedZodiac = it }, isDarkTheme)
            } else {
                // Если идёт загрузка
                if (isLoading) {
                    CircularProgressIndicator(color = TextColor)
                } else if (error != null) {
                    // Если возникла ошибка
                    Text(
                        text = error!!,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                } else {
                    // Выводим название знака и текст гороскопа
                    Text(
                        text = zodiacList[selectedZodiac!!],
                        style = MaterialTheme.typography.titleLarge,
                        color = TextColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .clip(ButtonShape)
                            .background(BeigeColor.copy(alpha = 0.9f))
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                    Text(
                        text = horoscopeText,
                        color = TextColor,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(ButtonShape)
                            .background(BeigeColor.copy(alpha = 0.9f))
                            .padding(24.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Источник данных
                    Text(
                        text = "Источник: 1001goroskop.ru",
                        color = TextColor,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Кнопка возврата к выбору знака зодиака (с иконкой)
                    Button(
                        onClick = { selectedZodiac = null; error = null },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BeigeColor,
                            contentColor = TextColor
                        ),
                        shape = ButtonShape,
                        modifier = Modifier
                            .width(64.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.nazad),
                            contentDescription = "Назад к выбору знака",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
        // Кнопка 'Назад' внизу экрана (дублирует onBackClick)
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
                    text = "Выход",
                    color = TextColor,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    // эффект загрузки гороскопа при выборе знака
    LaunchedEffect(selectedZodiac) {
        if (selectedZodiac != null) {
            isLoading = true
            error = null
            horoscopeText = ""
            val zodiacIndex = selectedZodiac!!
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val cacheKey = "${zodiacIndex}|$date"
            val now = System.currentTimeMillis()
            val db = TarotDatabase.getInstance(context)
            val dao = db.horoscopeCacheDao()
            try {
                // сначала ищем в базе Room
                val cached = withContext(Dispatchers.IO) { dao.getCache(cacheKey) }
                if (cached != null && now - cached.timestamp < 24 * 60 * 60 * 1000) {
                    horoscopeText = cached.text
                    isLoading = false
                } else {
                    // загружаем гороскоп с сайта
                    val text = withContext(Dispatchers.IO) {
                        fetchHoroscope1001(zodiacIndex)
                    }
                    horoscopeText = text
                    // сохраняем в Room
                    val entity = HoroscopeCacheEntity(
                        key = cacheKey,
                        zodiacIndex = zodiacIndex,
                        date = date,
                        text = text,
                        timestamp = now
                    )
                    withContext(Dispatchers.IO) { dao.insertCache(entity) }
                    isLoading = false
                }
            } catch (e: Exception) {
                error = "Гороскоп временно недоступен, попробуйте позже"
                isLoading = false
            }
        }
    }
}

/**
 * Сетка для выбора знака зодиака.
 * @param onSelect - обработчик выбора (возвращает индекс знака)
 */
@Composable
fun ZodiacGrid(onSelect: (Int) -> Unit, isDarkTheme: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        for (row in 0 until 4) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (col in 0 until 3) {
                    val index = row * 3 + col
                    if (index < zodiacList.size) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { onSelect(index) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(BeigeColor.copy(alpha = 0.9f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = zodiacIcons[index]),
                                    contentDescription = zodiacList[index],
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                            Text(
                                text = zodiacList[index],
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * получение гороскопа на день для выбранного знака с сайта 1001goroskop.ru.
 * @param zodiacIndex - индекс знака зодиака (0..11)
 * @return текст предсказания
 */
suspend fun fetchHoroscope1001(zodiacIndex: Int): String {
    val zodiacCodes = listOf(
        "aries", "taurus", "gemini", "cancer", "leo", "virgo",
        "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces"
    )
    val code = zodiacCodes[zodiacIndex]
    val url = "https://1001goroskop.ru/?znak=$code"
    val doc = Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
        .timeout(10000)
        .get()
    // обычно нужный текст — это первый <td> или <div> с длинным текстом
    val prediction = doc.select("td").firstOrNull { it.text().length > 100 }?.text()
        ?: doc.select("div").firstOrNull { it.text().length > 100 }?.text()
        ?: throw Exception("Не удалось найти гороскоп на $url")
    return prediction
}

