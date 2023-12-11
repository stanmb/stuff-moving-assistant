package ru.svrd.stuff_moving_assistant.domain.moving_box

import java.time.LocalDate

data class CreateMovingBoxDto(
    val title: String,
)

data class MovingBox(
    val id: Long,
    val sessionId: Long,
    val title: String,
    val imageURL: String?,
    val qrURL: String?,
    val archived: Boolean,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
    val extras: MovingBoxExtras
)

data class MovingBoxExtras(
    val items: List<String>?
)