package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteException
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteNaoEncontradoException
import edu.fatec.jvproject.aprovafacil.mapper.ClienteMapper
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.repository.IClienteRepository
import org.springframework.stereotype.Service

@Service
class ClienteService(val clienteRepository: IClienteRepository) : IClienteService {

        override fun salvarClienteComInformacoes(clienteDto: ClienteDto): Cliente {

            val clienteEncontrado = clienteRepository.findByCpf(clienteDto.cpf)
            if (clienteEncontrado != null)
                throw ClienteException("CPF ${clienteDto.cpf} já cadastrado")

            val clienteASerSalvo = ClienteMapper().to(clienteDto)

            clienteDto.participante?.let { cpfParticipante ->
                val participante = clienteRepository.findByCpf(cpfParticipante)
                    ?: throw ClienteException("Participante não encontrado para o CPF $cpfParticipante")
                clienteASerSalvo.participante = participante
            }

            return clienteRepository.save(clienteASerSalvo)
        }

    override fun buscarClientePeloCpf(cpf: String): Cliente {
        return clienteRepository.findByCpf(cpf)
            ?: throw ClienteNaoEncontradoException("Cliente com CPF $cpf não encontrado.")
    }

    override fun buscarClientePeloId(idCliente: Long): Cliente {
        return clienteRepository.findById(idCliente)
            .orElseThrow{ ClienteNaoEncontradoException("Cliente com ID $idCliente não encontrado.")}
    }

    override fun buscarClientesPeloStatus(status : StatusCliente): List<Cliente> {
        return clienteRepository.findAllByStatusCadastro(status)
    }

    override fun listarClientes(): List<Cliente> {
        return clienteRepository.findAll()
    }

    override fun atualizarCliente(cliente: Cliente): Cliente {
        if (cliente.id == null) {
            throw ClienteException("Necessário passar o ID do cliente para atualização de cadastro.")
        }

        val clienteExistente = clienteRepository.findById(cliente.id!!)
            .orElseThrow { ClienteNaoEncontradoException("Cliente com ID ${cliente.id} não encontrado.") }

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
            .orElseThrow{ ClienteNaoEncontradoException("Cliente não encontrado para exclusão")}

        clienteRepository.delete(cliente)
    }

}