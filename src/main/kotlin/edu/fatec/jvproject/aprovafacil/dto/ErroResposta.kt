package edu.fatec.jvproject.aprovafacil.dto

import java.time.LocalDateTime

data class ErroResposta(
    val timestamp: LocalDateTime,
    val status: Int,
    val mensagem: String
)