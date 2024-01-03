package ru.svrd.stuff_moving_assistant

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.svrd.stuff_moving_assistant.domain.moving_box.MovingBox
import ru.svrd.stuff_moving_assistant.domain.moving_box.MovingBoxExtras
import ru.svrd.stuff_moving_assistant.domain.moving_box.MovingBoxService
import ru.svrd.stuff_moving_assistant.infrastructure.repository.MovingBoxRepository
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mock

@ExtendWith(MockitoExtension::class)
class MovingBoxServiceTest {
    private val sessionId = 100L
    private val title = "test"
    private val movingBox = MovingBox(
        10,
        sessionId,
        title,
        null,
        null,
        false,
        LocalDate.now(),
        LocalDate.now(),
        MovingBoxExtras(listOf("test"))
    )
    private val movingBox2 = MovingBox(
        11,
        sessionId,
        title,
        null,
        null,
        false,
        LocalDate.now(),
        LocalDate.now(),
        MovingBoxExtras(listOf("test"))
    )

    @Mock
    private lateinit var movingBoxRepository: MovingBoxRepository

    @Test
    fun addBoxTest() {
        `when`(movingBoxRepository.saveNewMovingBox(sessionId, title)).thenReturn(movingBox)
        val service = MovingBoxService(movingBoxRepository)
        val box = service.newBox(sessionId, title)
        assertEquals(box, movingBox, "Boxes are not equal")
    }

    @Test
    fun editExtrasTest() {
        val newExtras = MovingBoxExtras(listOf("test", "test1"))
        `when`(movingBoxRepository.editItems(sessionId, movingBox.id, newExtras)).thenReturn(
            movingBox.copy(extras = newExtras)
        )
        val service = MovingBoxService(movingBoxRepository)
        val box = service.editExtras(sessionId, movingBox.id, newExtras)
        assertEquals(box.extras,newExtras)
    }

    @Test
    fun findBoxesInSessionTest() {
        `when`(movingBoxRepository.findBoxesBySessionId(sessionId, mapOf("item" to "test"))).thenReturn(
            listOf(movingBox, movingBox2)
        )
        val service = MovingBoxService(movingBoxRepository)
        val listOfBoxes = service.findBoxesInSession(sessionId,  "test")
        assertEquals(listOfBoxes, listOf(movingBox, movingBox2))
    }
}