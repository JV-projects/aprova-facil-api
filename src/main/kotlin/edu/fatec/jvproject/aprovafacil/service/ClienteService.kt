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

    override fun atualizarCliente(cliente: Cliente) {
        if (cliente.id == null)
            throw RuntimeException("Necessário passar o ID do cliente para atualização de cadastro.")

        val clienteExistente = clienteRepository.findById(cliente.id!!)
            .orElseThrow { RuntimeException("Cliente não encontrado para exclusão") }

        val clienteAtualizado = cliente.copy(id = clienteExistente.id)

        clienteRepository.save(clienteAtualizado)
    }

    override fun deletarCliente(id: Long) {
        val cliente = clienteRepository.findById(id)
            .orElseThrow { RuntimeException("Cliente não encontrado para exclusão") }

        clienteRepository.delete(cliente)
    }

}