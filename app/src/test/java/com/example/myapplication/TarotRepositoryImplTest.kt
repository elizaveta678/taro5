import com.example.myapplication.data.local.TarotCardDao
import com.example.myapplication.data.local.TarotCardEntity
import com.example.myapplication.data.repository.TarotRepositoryImpl
import com.example.myapplication.domain.model.TarotCard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


@OptIn(ExperimentalCoroutinesApi::class)
class TarotRepositoryImplTest {
    private lateinit var dao: TarotCardDao
    private lateinit var repository: TarotRepositoryImpl

    private val testEntities = listOf(
        TarotCardEntity("img1", "desc1"),
        TarotCardEntity("img2", "desc2")
    )
    private val testCards = listOf(
        TarotCard("img1", "desc1"),
        TarotCard("img2", "desc2")
    )

    @Before
    fun setup() {
        dao = mock()
        repository = TarotRepositoryImpl(dao)
    }

    @Test
    fun `getAllCards returns mapped domain models`() = runBlocking {
        whenever(dao.getAllCards()).thenReturn(flowOf(testEntities))
        val result = repository.getAllCards()
        val list = mutableListOf<List<TarotCard>>()
        result.collect { list.add(it) }
        assertEquals(testCards, list.first())
    }

    @Test
    fun `getRandomCards returns mapped domain models`() = runBlocking {
        whenever(dao.getRandomCards()).thenReturn(testEntities)
        val result = repository.getRandomCards()
        assertEquals(testCards, result)
    }

    @Test
    fun `getAllCardsOnce returns mapped domain models`() = runBlocking {
        whenever(dao.getAllCardsOnce()).thenReturn(testEntities)
        val result = repository.getAllCardsOnce()
        assertEquals(testCards, result)
    }

    @Test
    fun `insertCards calls dao with mapped entities`() = runBlocking {
        val cards = listOf(TarotCard("img3", "desc3"))
        repository.insertCards(cards)
        Mockito.verify(dao).insertCards(listOf(TarotCardEntity("img3", "desc3")))
    }
} 