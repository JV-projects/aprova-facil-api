package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.model.Cliente

interface IClienteService {
    fun salvarClienteComInformacoes(clienteDto: ClienteDto): Cliente
    fun buscarClientePeloCpf(cpf: String): Cliente
    fun buscarClientePeloId(idCliente: Long): Cliente
    fun buscarClientesPeloStatus(status: StatusCliente): List<Cliente>
    fun listarClientes(): List<Cliente>
    fun atualizarCliente(clienteDto: ClienteDto): Cliente
    fun atualizarStatusCliente(clienteId: Long, novoStatus: StatusCliente): Cliente
    fun deletarCliente(id: Long)
}