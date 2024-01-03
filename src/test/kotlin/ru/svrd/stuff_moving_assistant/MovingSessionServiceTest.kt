package ru.svrd.stuff_moving_assistant

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import ru.svrd.stuff_moving_assistant.domain.moving_session.MovingSessionService
import ru.svrd.stuff_moving_assistant.infrastructure.repository.MovingSessionRepository
import org.mockito.Mockito.`when`
import ru.svrd.stuff_moving_assistant.domain.moving_session.MovingSession
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals

@ExtendWith(MockitoExtension::class)
class MovingSessionServiceTest {
    private val sessionId = 100L
    private val title = "Test title"
    private val ownerId = 10L

    private val movingSession = MovingSession(
        sessionId,
        10L,
        title,
        LocalDate.now()
    )

    @Mock
    private lateinit var movingSessionRepository: MovingSessionRepository

    @Test
    fun newSessionTest() {
        val movingSessionService = MovingSessionService(movingSessionRepository)
        `when`(movingSessionRepository.saveNewMovingSession(title, ownerId)).thenReturn(movingSession)
        val session = movingSessionService.newSession(title, ownerId)
        assertEquals(movingSession.toCreateMovingSessionResponse(), session)
    }

    @Test
    fun getSessionTest() {
        val movingSessionService = MovingSessionService(movingSessionRepository)
        `when`(movingSessionRepository.getSessionById(sessionId)).thenReturn(movingSession)
        val session = movingSessionService.getSession(sessionId)
        assertEquals(movingSession, session)
    }
}