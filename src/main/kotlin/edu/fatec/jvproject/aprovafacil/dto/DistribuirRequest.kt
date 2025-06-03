package edu.fatec.jvproject.aprovafacil.dto

data class DistribuirRequest(
    val idCliente: Long,
    val emailCorretor: String,
    val analiseCredito:String
)
