package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.EstadoImovel
import edu.fatec.jvproject.aprovafacil.enum.TipoImovel
import jakarta.validation.constraints.NotNull

data class DadosInteresseDto(

    @field:NotNull(message = "Informe o 'tipo de imóvel'.")
    val tipoImovel: TipoImovel,
    
    @field:NotNull(message = "Informe o 'estado do imóvel'.")
    val estadoImovel: EstadoImovel
)