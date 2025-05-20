package edu.fatec.jvproject.aprovafacil.repository

import edu.fatec.jvproject.aprovafacil.model.DocumentoCliente
import org.springframework.data.jpa.repository.JpaRepository

interface IDocumentoClienteRepository : JpaRepository<DocumentoCliente, Long> {
    fun findAllByClienteId(clienteId: Long) : List<DocumentoCliente>
}