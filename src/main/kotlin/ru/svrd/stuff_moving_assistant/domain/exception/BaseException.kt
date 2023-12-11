package ru.svrd.stuff_moving_assistant.domain.exception

import org.springframework.http.HttpStatusCode
import java.lang.RuntimeException

open class BaseException(
    val httpCode: HttpStatusCode,
    override val message: String,
): RuntimeException(message)

data class ErrorDto(
    val status: String = "error",
    val message: String
)