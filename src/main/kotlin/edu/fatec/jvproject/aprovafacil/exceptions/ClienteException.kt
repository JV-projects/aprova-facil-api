package edu.fatec.jvproject.aprovafacil.exceptions

class ClienteException(mensagem: String) : RuntimeException(mensagem)
class ClienteNaoEncontradoException(mensagem: String) : RuntimeException(mensagem)