package edu.fatec.jvproject.aprovafacil.repository

import edu.fatec.jvproject.aprovafacil.model.Atendimento
import edu.fatec.jvproject.aprovafacil.model.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IAtendimentoRepository : JpaRepository<Atendimento, Long> {
    fun findByCliente(cliente: Cliente): Atendimento?
    fun findByCliente_Id(clienteId: Long): Atendimento?
}