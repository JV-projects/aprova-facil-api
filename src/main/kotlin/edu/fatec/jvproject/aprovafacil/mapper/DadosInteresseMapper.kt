package edu.fatec.jvproject.aprovafacil.mapper

import edu.fatec.jvproject.aprovafacil.dto.DadosInteresseDto
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse

fun DadosInteresseDto.toEntity(): DadosInteresse {
    return DadosInteresse(
        tipoImovel = this.tipoImovel,
        estadoImovel = this.estadoImovel
    )
}