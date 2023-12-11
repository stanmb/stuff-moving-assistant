package ru.svrd.stuff_moving_assistant.application.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.svrd.stuff_moving_assistant.domain.exception.BaseException
import ru.svrd.stuff_moving_assistant.domain.exception.ErrorDto

@ControllerAdvice
class ExceptionController : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [
        BaseException::class
    ])
    fun handleCustomException(
        ex: BaseException, request: WebRequest
    ): ResponseEntity<ErrorDto> {
        return this.buildResponseEntity(ex)
    }

    private fun buildResponseEntity(ex: BaseException): ResponseEntity<ErrorDto> {
        return ResponseEntity(ErrorDto(message = ex.message), ex.httpCode)
    }
}