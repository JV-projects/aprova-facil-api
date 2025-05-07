package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.TipoImovel
import jakarta.validation.constraints.NotNull

data class DadosInteresseDto(

    @field:NotNull(message = "Informe o 'tipo de imóvel'.")
    val tipoImovel: TipoImovel,
    
    val regiaoInteresse: String? = null
)