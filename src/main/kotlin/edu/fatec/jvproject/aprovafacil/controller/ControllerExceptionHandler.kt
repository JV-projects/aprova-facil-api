package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.BaseResponseEntity
import edu.fatec.jvproject.aprovafacil.dto.ErroResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@ControllerAdvice(annotations = [RestController::class])
class ControllerExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ErroResponseEntity> {
        val erro = ErroResponseEntity(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            mensagem = e.message ?: "Erro interno no servidor"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<out BaseResponseEntity> {

        var erro = ErroResponseEntity(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            mensagem = e.message ?: "Erro interno no servidor"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(erro)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleBadRequest(e: RuntimeException): ResponseEntity<Any> {
        var erro = ErroResponseEntity(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            mensagem = e.message ?: "Erro interno no servidor"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(erro)
    }
}