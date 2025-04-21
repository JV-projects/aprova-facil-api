package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.repository.IClienteRepository
import org.springframework.stereotype.Service

@Service
class ClienteService(val clienteRepository: IClienteRepository) : IClienteService {

    override fun salvarClienteComInformacoes(cliente: Cliente): Cliente {

        val clienteEncontrado = clienteRepository.findByCpf(cliente.cpf)
        if (clienteEncontrado != null)
            throw RuntimeException("CPF ${cliente.cpf} já cadastrado")

        return clienteRepository.save(cliente)
    }

    override fun buscarClientePeloCpf(cpf: String): Cliente? {
        val cliente = clienteRepository.findByCpf(cpf)
        return cliente
    }

    override fun listarClientes(): List<Cliente> {
        return clienteRepository.findAll()
    }

}