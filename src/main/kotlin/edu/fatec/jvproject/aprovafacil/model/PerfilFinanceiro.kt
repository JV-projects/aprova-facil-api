package edu.fatec.jvproject.aprovafacil.model

import edu.fatec.jvproject.aprovafacil.enum.TipoRenda
import jakarta.persistence.Embeddable

@Embeddable
data class PerfilFinanceiro(
    val rendaBruta: Double,
    val tipoRenda: TipoRenda,
    val possuiRestricao: Boolean? = null,
    val possuiDependente: Boolean? = null,
)