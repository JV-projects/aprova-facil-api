package edu.fatec.jvproject.aprovafacil.dto

data class AtendimentoRequest(
    val idCliente: Long,
    val emailCorretor: String,
    val analiseCredito: String
)
