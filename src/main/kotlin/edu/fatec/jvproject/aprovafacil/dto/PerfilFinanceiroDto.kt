package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.TipoRenda
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class PerfilFinanceiroDto(

    @field:NotNull(message = "Informe a 'renda bruta'.")
    @field:Positive(message = "A 'renda bruta' deve ser maior que zero.")
    var rendaBruta: Double,

    @field:NotNull(message = "Informe o 'tipo de renda'.")
    var tipoRenda: TipoRenda,

    var possuiRestricao: Boolean? = false,

    var possuiDependente: Boolean? = false,

    var tresAnosFgts: Boolean? = false,

    var usarFgts: Boolean? = false,
)
