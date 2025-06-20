package edu.fatec.jvproject.aprovafacil.dto

import java.time.LocalDateTime

abstract class BaseResponseEntity(
    val timestamp: LocalDateTime,
    val status: Int,
    val mensagem: String
)