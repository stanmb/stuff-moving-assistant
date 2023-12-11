package ru.svrd.stuff_moving_assistant.domain.moving_session

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.svrd.stuff_moving_assistant.domain.exception.BaseException
import ru.svrd.stuff_moving_assistant.infrastructure.repository.MovingSessionRepository

@Service
class MovingSessionService(private val movingSessionRepository: MovingSessionRepository){

    fun newSession(title: String, ownerId: Long = 1): CreateMovingSessionResponse {
        return movingSessionRepository.saveNewMovingSession(title, ownerId)?.toCreateMovingSessionResponse()
            ?: throw BaseException(
                message = "Cannot insert moving session with title $title",
                httpCode = HttpStatus.INTERNAL_SERVER_ERROR
            )
    }

    fun getSession(sessionId: Long): MovingSession {
        return movingSessionRepository.getSessionById(sessionId)
            ?: throw BaseException(
                message = "No session with id $sessionId",
                httpCode = HttpStatus.NOT_FOUND
            )
    }

}