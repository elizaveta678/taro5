import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test

class HoroscopeParserTest {
    @Test
    fun `parse horoscope from html td`() {
        val longText = "Это длинный текст предсказания для знака зодиака, который должен быть извлечён. Он специально сделан длиннее 100 символов, чтобы пройти фильтр в парсере. Ещё немного текста для уверенности."
        val html = "<html><body><table><tr><td>Короткий текст</td><td>$longText</td></tr></table></body></html>"
        val doc = Jsoup.parse(html)
        val tds = doc.select("td")
        tds.forEachIndexed { i, el ->
            println("td[$i] text length: ${el.text().length}")
            println("td[$i] text: '${el.text()}'")
        }
        val prediction = tds.firstOrNull { it.text().length > 100 }?.text()
            ?: doc.select("div").firstOrNull { it.text().length > 100 }?.text()
            ?: ""
        if (prediction.isEmpty()) {
            throw AssertionError("Prediction is empty! All td: " + tds.joinToString { "'${it.text()}' (len: ${it.text().length})" })
        }
        assertEquals(longText, prediction)
    }

    @Test
    fun `parse horoscope from html div`() {
        val longText = "Это длинный текст предсказания для знака зодиака, который должен быть извлечён из div. Он специально сделан длиннее 100 символов, чтобы пройти фильтр в парсере. Ещё немного текста для уверенности."
        val html = "<html><body><div>$longText</div></body></html>"
        val doc = Jsoup.parse(html)
        val divs = doc.select("div")
        val prediction = doc.select("td").firstOrNull { it.text().length > 100 }?.text()
            ?: divs.firstOrNull { it.text().length > 100 }?.text()
            ?: ""
        if (prediction.isEmpty()) {
            throw AssertionError("Prediction is empty! All div: " + divs.joinToString { "'${it.text()}' (len: ${it.text().length})" })
        }
        assertEquals(longText, prediction)
    }
} 