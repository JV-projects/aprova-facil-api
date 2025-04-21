package edu.fatec.jvproject.aprovafacil.model

import edu.fatec.jvproject.aprovafacil.enum.TipoImovel
import jakarta.persistence.Embeddable

@Embeddable
data class DadosInteresse(
    val tipoImovel: TipoImovel,
    val regiaoInteresse: String? = null,
)