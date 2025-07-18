package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.ErroResponseEntity
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteException
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteNaoEncontradoException
import edu.fatec.jvproject.aprovafacil.exceptions.DocumentoException
import edu.fatec.jvproject.aprovafacil.exceptions.TokenException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@ControllerAdvice(annotations = [RestController::class])
class ControllerExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ErroResponseEntity> {
        val erro = ErroResponseEntity(
            timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            mensagem = "Erro interno no servidor"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro)
    }

    @ExceptionHandler(DocumentoException::class)
    fun handleBadRequest(e: DocumentoException): ResponseEntity<ErroResponseEntity> {

        var erro = ErroResponseEntity(
            timestamp = LocalDateTime.now().withNano(0),
            status = HttpStatus.BAD_REQUEST.value(),
            mensagem = e.message ?: "Erro interno no servidor"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(erro)
    }

    @ExceptionHandler(ClienteException::class)
    fun handleBadRequest(e: ClienteException): ResponseEntity<ErroResponseEntity> {
        var erro = ErroResponseEntity(
            timestamp = LocalDateTime.now().withNano(0),
            status = HttpStatus.BAD_REQUEST.value(),
            mensagem = e.message ?: "Erro interno no servidor"
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(erro)
    }

    @ExceptionHandler(ClienteNaoEncontradoException::class)
    fun handleBadRequest(e: ClienteNaoEncontradoException): ResponseEntity<ErroResponseEntity> {
        var erro = ErroResponseEntity(
            timestamp = LocalDateTime.now().withNano(0),
            status = HttpStatus.NOT_FOUND.value(),
            mensagem = e.message ?: "Erro interno no servidor"
        )

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(erro)
    }

    @ExceptionHandler(TokenException::class)
    fun handleTokenException(e: TokenException): ResponseEntity<ErroResponseEntity>{
        var erro = ErroResponseEntity(
            timestamp = LocalDateTime.now().withNano(0),
            status = HttpStatus.NOT_FOUND.value(),
            mensagem = e.message ?: "Erro interno no servidor"
        )

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(erro)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErroResponseEntity> {
        val mensagens = e.bindingResult.fieldErrors.joinToString("; ") {
            "${it.defaultMessage}"
        }

        val erro = ErroResponseEntity(
            timestamp = LocalDateTime.now().withNano(0),
            status = HttpStatus.BAD_REQUEST.value(),
            mensagem = mensagens
        )

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(erro)
    }
}