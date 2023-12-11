package ru.svrd.stuff_moving_assistant.infrastructure.repository

import ru.svrd.stuff_moving_assistant.domain.moving_box.MovingBox
import ru.svrd.stuff_moving_assistant.domain.moving_box.MovingBoxExtras

interface MovingBoxRepository {
    fun saveNewMovingBox(sessionId: Long, title: String): MovingBox?
    fun editItems(sessionId: Long, boxId: Long, newItems: MovingBoxExtras): MovingBox?
    fun findBoxesBySessionId(sessionId: Long, query: Map<String, String>): List<MovingBox>
}