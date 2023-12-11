package ru.svrd.stuff_moving_assistant.infrastructure.repository

import ru.svrd.stuff_moving_assistant.domain.moving_session.MovingSession

interface MovingSessionRepository {

    fun saveNewMovingSession(title: String, ownerId: Long): MovingSession?

    fun getSessionById(sessionId: Long): MovingSession?
}