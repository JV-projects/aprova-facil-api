package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.model.Cliente

interface IClienteService {
    fun salvarClienteComInformacoes(cliente: Cliente) : Cliente
    fun buscarClientePeloCpf(cpf: String) : Cliente
    fun buscarClientePeloId(id: Long) : Cliente
    fun buscarClientesPeloStatus(status: StatusCliente) : List<Cliente>
    fun listarClientes() : List<Cliente>
    fun atualizarCliente(cliente: Cliente) : Cliente
    fun deletarCliente(id: Long)
}