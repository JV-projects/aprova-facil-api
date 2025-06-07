package edu.fatec.jvproject.aprovafacil.dto

data class AtendimentoDTO(
    val id: Long? = null,
    val idCliente: Long,
    val analiseCredito: String,
    val emailCorretor: String,
    val devolutiva: String? = null
)