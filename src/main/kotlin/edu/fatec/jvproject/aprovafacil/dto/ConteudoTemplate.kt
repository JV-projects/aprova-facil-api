package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.EstadoImovel
import edu.fatec.jvproject.aprovafacil.enum.TipoImovel

data class ConteudoTemplate(
    val clienteNome: String,
    val telefone: String,
    val email: String,
    val interesseImovel: TipoImovel,
    val estadoImovel: EstadoImovel,
    val analiseCredito: String,
    val codigoDevolutiva: String
)
