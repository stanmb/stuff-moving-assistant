package ru.svrd.stuff_moving_assistant.infrastructure.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.svrd.stuff_moving_assistant.domain.moving_box.MovingBox
import ru.svrd.stuff_moving_assistant.domain.moving_box.MovingBoxExtras
import java.sql.ResultSet
import java.time.LocalDateTime

@Repository
class MovingBoxJDBCRepository(
    private val masterNamedJdbcTemplate: NamedParameterJdbcTemplate,
    private val objectMapper: ObjectMapper,
): MovingBoxRepository {

    private val movingBoxMapper = { rs: ResultSet, _: Int ->
        MovingBox(
            id = rs.getLong("id"),
            sessionId = rs.getLong("session_id"),
            title = rs.getString("title"),
            imageURL = rs.getString("img_url"),
            qrURL = rs.getString("qr_url"),
            archived = rs.getBoolean("archived"),
            createdAt = rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
            updatedAt = rs.getTimestamp("updated_at").toLocalDateTime().toLocalDate(),
            extras = objectMapper.readValue(rs.getString("extras"), MovingBoxExtras::class.java)
        )
    }

    override fun saveNewMovingBox(sessionId: Long, title: String): MovingBox? {
        val sql = """
            INSERT INTO moving_box (
                session_id,
                title,
                archived,
                created_at,
                updated_at
            ) VALUES (
                :session_id,
                :title,
                :archived,
                :created_at,
                :updated_at
            ) RETURNING id, session_id, title, img_url, qr_url, archived, created_at, updated_at, extras
        """.trimIndent()

        val parameterSource = MapSqlParameterSource()
            .addValue("session_id", sessionId)
            .addValue("title", title)
            .addValue("archived", false)
            .addValue("created_at", LocalDateTime.now().toLocalDate())
            .addValue("updated_at", LocalDateTime.now().toLocalDate())

        return masterNamedJdbcTemplate.query(sql, parameterSource, movingBoxMapper).firstOrNull()
    }

    override fun editItems(sessionId: Long, boxId: Long, newItems: MovingBoxExtras): MovingBox? {
        val sql = """
            UPDATE moving_box SET extras = :extras::JSONB
            WHERE session_id = :sessionId AND id = :id
            RETURNING id, session_id, title, img_url, qr_url, archived, created_at, updated_at, extras
        """.trimIndent()

        val parameterSource = MapSqlParameterSource()
            .addValue("sessionId", sessionId)
            .addValue("id", boxId)
            .addValue("extras", objectMapper.writeValueAsString(newItems))

        return masterNamedJdbcTemplate.query(sql, parameterSource, movingBoxMapper).firstOrNull()
    }

    override fun findBoxesBySessionId(sessionId: Long, query: Map<String, String>): List<MovingBox> {
        val sqlQuery = StringBuilder()

        val parameterSource = MapSqlParameterSource()
            .addValue("sessionId", sessionId)

        when {
            query.containsKey("item") -> {
                sqlQuery.append("AND (extras->'items')::jsonb ?? :item")
                parameterSource.addValue("item", query["item"])
            }
        }

        val sql = """
            SELECT * FROM moving_box
            WHERE session_id = :sessionId $sqlQuery
        """.trimIndent()

        return masterNamedJdbcTemplate.query(sql, parameterSource, movingBoxMapper)
    }
}