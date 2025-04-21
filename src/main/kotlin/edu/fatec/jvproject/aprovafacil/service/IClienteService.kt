package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse

interface IClienteService {
    fun salvarClienteComInformacoes(cliente: Cliente) : Cliente
    fun buscarClientePeloCpf(cpf: String) : Cliente?
    fun listarClientes() : List<Cliente>
}