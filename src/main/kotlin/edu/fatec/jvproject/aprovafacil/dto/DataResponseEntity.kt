package edu.fatec.jvproject.aprovafacil.dto

import java.time.LocalDateTime

class DataResponseEntity<T>(
    val data: T,
    timestamp: LocalDateTime,
    status: Int,
    mensagem: String
) : BaseResponseEntity(timestamp, status, mensagem)