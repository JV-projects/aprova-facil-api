package edu.fatec.jvproject.aprovafacil.repository

import edu.fatec.jvproject.aprovafacil.model.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IClienteRepository : JpaRepository<Cliente, Long>{
    fun findByCpf(cpf: String) : Cliente?
}