package edu.fatec.jvproject.aprovafacil.mapper

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.model.Atendimento

fun Atendimento.toDto() : AtendimentoDTO{
    return AtendimentoDTO(
        id= this.id,
        idCliente = this.cliente.id!!,
        analiseCredito = this.analiseCredito,
        emailCorretor = this.emailCorretor,
        devolutiva = this.devolutiva
    )
}