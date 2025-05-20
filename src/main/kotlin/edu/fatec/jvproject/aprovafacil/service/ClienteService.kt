package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
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

    override fun buscarClientePeloId(idCliente: Long): Cliente? {
        val cliente = clienteRepository.findById(idCliente)
        return cliente.get()
    }

    override fun buscarClientesPeloStatus(status : StatusCliente): List<Cliente> {
        return clienteRepository.findAllByStatusCadastro(status)
    }

    override fun listarClientes(): List<Cliente> {
        return clienteRepository.findAll()
    }

    override fun atualizarCliente(cliente: Cliente): Cliente {
        if (cliente.id == null) {
            throw RuntimeException("Necessário passar o ID do cliente para atualização de cadastro.")
        }

        val clienteExistente = clienteRepository.findById(cliente.id!!)
            .orElseThrow { RuntimeException("Cliente com ID ${cliente.id} não encontrado.") }

        clienteExistente.apply {
            nome = cliente.nome
            cpf = cliente.cpf
            email = cliente.email
            telefone = cliente.telefone
            dataNascimento = cliente.dataNascimento
            statusCadastro = cliente.statusCadastro
            perfilFinanceiro = cliente.perfilFinanceiro
            dadosInteresse = cliente.dadosInteresse
        }

        return clienteRepository.save(clienteExistente)
    }

    override fun deletarCliente(id: Long) {
        val cliente = clienteRepository.findById(id)
            .orElseThrow{ RuntimeException("Cliente não encontrado para exclusão")}

        clienteRepository.delete(cliente)
    }

}