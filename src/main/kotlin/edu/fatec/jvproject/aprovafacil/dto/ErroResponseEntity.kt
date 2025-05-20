package edu.fatec.jvproject.aprovafacil.dto

import java.time.LocalDateTime

class ErroResponseEntity(
    timestamp: LocalDateTime,
    status: Int,
    mensagem: String
) : BaseResponseEntity(timestamp, status, mensagem);