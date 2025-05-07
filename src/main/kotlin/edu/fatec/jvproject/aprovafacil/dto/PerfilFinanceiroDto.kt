package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.TipoRenda
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PerfilFinanceiroDto(

    @field:NotNull(message = "Informe a 'renda bruta'.")
    @field:Positive(message = "A 'renda bruta' deve ser maior que zero.")
    val rendaBruta: Double,

    @field:NotNull(message = "Informe o 'tipo de renda'.")
    val tipoRenda: TipoRenda,

    val possuiRestricao: Boolean? = null,

    val possuiDependente: Boolean? = null
)
