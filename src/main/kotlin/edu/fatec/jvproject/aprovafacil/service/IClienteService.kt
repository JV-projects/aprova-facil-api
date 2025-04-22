package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.model.Cliente

interface IClienteService {
    fun salvarClienteComInformacoes(cliente: Cliente) : Cliente
    fun buscarClientePeloCpf(cpf: String) : Cliente?
    fun listarClientes() : List<Cliente>
    fun atualizarCliente(cliente: Cliente)
    fun deletarCliente(id: Long)
}