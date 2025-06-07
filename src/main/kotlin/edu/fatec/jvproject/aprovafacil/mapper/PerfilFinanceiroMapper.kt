package edu.fatec.jvproject.aprovafacil.mapper

import edu.fatec.jvproject.aprovafacil.dto.PerfilFinanceiroDto
import edu.fatec.jvproject.aprovafacil.model.PerfilFinanceiro

fun PerfilFinanceiroDto.toEntity(): PerfilFinanceiro {
    return PerfilFinanceiro(
        rendaBruta = this.rendaBruta,
        tipoRenda = this.tipoRenda,
        possuiRestricao = this.possuiRestricao,
        possuiDependente = this.possuiDependente,
        tresAnosFgts = this.tresAnosFgts,
        usarFgts = this.usarFgts
    )
}