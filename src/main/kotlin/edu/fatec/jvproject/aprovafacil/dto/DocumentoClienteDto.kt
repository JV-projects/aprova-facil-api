package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.TipoDocumento

data class DocumentoClienteDto(
    val tipo: TipoDocumento,
    val nomeArquivo: String
)
