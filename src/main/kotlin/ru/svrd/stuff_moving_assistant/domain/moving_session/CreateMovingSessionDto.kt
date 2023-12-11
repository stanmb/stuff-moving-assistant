package ru.svrd.stuff_moving_assistant.domain.moving_session

import java.time.LocalDate

data class CreateMovingSessionDto(
    val title: String
)

data class CreateMovingSessionResponse(
    val id: Long,
    val title: String,
    val createdAt: LocalDate,
)

data class MovingSession(
    val id: Long,
    val ownerId: Long,
    val title: String,
    val createdAt: LocalDate
) {
    fun toCreateMovingSessionResponse() = CreateMovingSessionResponse(
        this.id,
        this.title,
        this.createdAt
    )
}