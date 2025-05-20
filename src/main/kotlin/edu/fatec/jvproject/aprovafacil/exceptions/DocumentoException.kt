package edu.fatec.jvproject.aprovafacil.exceptions

class DocumentoExistenteException(caminho: String) : RuntimeException("Já existe um documento nesse caminho: $caminho")