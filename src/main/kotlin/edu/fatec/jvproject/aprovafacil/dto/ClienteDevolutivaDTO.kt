package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.StatusCliente

data class ClienteDevolutivaDTO(
    val id: Long,
    val nome: String,
    val email: String,
    val status: StatusCliente
)
