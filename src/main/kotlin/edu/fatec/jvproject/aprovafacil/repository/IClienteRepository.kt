package edu.fatec.jvproject.aprovafacil.repository

import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.model.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface IClienteRepository : JpaRepository<Cliente, Long>{
    fun findByCpf(cpf: String) : Cliente?
    fun findAllByStatusCadastro(status: StatusCliente ) : List<Cliente>
}