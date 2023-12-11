package ru.svrd.stuff_moving_assistant.infrastructure.repository

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.svrd.stuff_moving_assistant.domain.moving_session.MovingSession
import java.sql.ResultSet
import java.time.LocalDateTime

@Repository
class MovingSessionJDBCRepository(
    private val masterNamedJdbcTemplate: NamedParameterJdbcTemplate
): MovingSessionRepository {

    private val movingSessionMapper = { rs: ResultSet, _: Int ->
        MovingSession(
            id = rs.getLong("id"),
            ownerId = rs.getLong("owner_id"),
            title = rs.getString("title"),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
        )
    }
    override fun saveNewMovingSession(title: String, ownerId: Long): MovingSession? {
        val sql = """
            INSERT INTO moving_session(
                owner_id,
                title,
                created_at
            ) VALUES (
                :owner_id,
                :title,
                :created_at
            ) RETURNING id, owner_id, title, created_at
        """.trimIndent()

        val parameterSource = MapSqlParameterSource()
            .addValue("owner_id", ownerId)
            .addValue("title", title)
            .addValue("created_at", LocalDateTime.now().toLocalDate())

        return masterNamedJdbcTemplate.query(sql, parameterSource, movingSessionMapper).firstOrNull()
    }

    override fun getSessionById(sessionId: Long): MovingSession? {
        val sql = """
            SELECT * FROM moving_session
            WHERE id = :id
        """.trimIndent()

        val parameterSource = MapSqlParameterSource()
            .addValue("id", sessionId)

        return masterNamedJdbcTemplate.query(sql, parameterSource, movingSessionMapper).firstOrNull()
    }
}