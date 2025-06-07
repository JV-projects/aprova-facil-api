package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteException
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteNaoEncontradoException
import edu.fatec.jvproject.aprovafacil.exceptions.DocumentoException
import edu.fatec.jvproject.aprovafacil.mapper.ClienteMapper
import edu.fatec.jvproject.aprovafacil.mapper.toEntity
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.repository.IClienteRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ClienteService(
    val clienteRepository: IClienteRepository,
    val documentoService: IDocumentoService
) : IClienteService {

    @Transactional
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

        if (clienteDto.documentos.isEmpty())
            throw DocumentoException("Nenhum documento foi enviado")

        val clienteSalvo = clienteRepository.save(clienteASerSalvo)

        val documentos = documentoService.processarDocumentosBase64(clienteDto.documentos, clienteSalvo)

        clienteSalvo.registroDocumentos.addAll(documentos)

        return clienteSalvo
    }

    override fun buscarClientePeloCpf(cpf: String): Cliente {
        return clienteRepository.findByCpf(cpf)
            ?: throw ClienteNaoEncontradoException("Cliente com CPF $cpf não encontrado.")
    }

    override fun buscarClientePeloId(idCliente: Long): Cliente {
        return clienteRepository.findById(idCliente)
            .orElseThrow { ClienteNaoEncontradoException("Cliente com ID $idCliente não encontrado.") }
    }

    override fun buscarClientesPeloStatus(status: StatusCliente): List<Cliente> {
        return clienteRepository.findAllByStatusCadastro(status)
    }

    override fun listarClientes(): List<Cliente> {
        return clienteRepository.findAll()
    }

    override fun atualizarCliente(clienteDto: ClienteDto): Cliente {
        if (clienteDto.id == null) {
            throw ClienteException("Necessário passar o ID do cliente para atualização de cadastro.")
        }

        val clienteEncontrado = clienteRepository.findByCpf(clienteDto.cpf)
        if (clienteEncontrado != null)
            throw ClienteException("CPF ${clienteDto.cpf} já cadastrado")

        val clienteExistente = clienteRepository.findById(clienteDto.id!!)
            .orElseThrow { ClienteNaoEncontradoException("Cliente com ID ${clienteDto.id} não encontrado.") }

        clienteExistente.apply {
            nome = clienteDto.nome
            cpf = clienteDto.cpf
            email = clienteDto.email
            telefone = clienteDto.telefone
            dataNascimento = clienteDto.dataNascimento
            perfilFinanceiro = clienteDto.perfilFinanceiro.toEntity()
            dadosInteresse = clienteDto.dadosInteresse.toEntity()
        }

        clienteDto.participante?.let { cpfParticipante ->
            val participante = clienteRepository.findByCpf(cpfParticipante)
                ?: throw ClienteException("Participante não encontrado para o CPF $cpfParticipante")
            clienteExistente.participante = participante
        }

        if (!clienteDto.documentos.isEmpty()){
            val documentos = documentoService.processarDocumentosBase64(clienteDto.documentos, clienteExistente)

            clienteExistente.registroDocumentos.clear()
            clienteExistente.registroDocumentos.addAll(documentos)
        }

        return clienteRepository.save(clienteExistente)
    }

    override fun atualizarStatusCliente(clienteId: Long, novoStatus: StatusCliente): Cliente {
        val cliente = clienteRepository.findById(clienteId)
            .orElseThrow { ClienteNaoEncontradoException("Cliente com ID $clienteId não encontrado.") }

        cliente.statusCadastro = novoStatus

        return clienteRepository.save(cliente)
    }

    override fun deletarCliente(id: Long) {
        val cliente = clienteRepository.findById(id)
            .orElseThrow { ClienteNaoEncontradoException("Cliente não encontrado para exclusão") }

        clienteRepository.delete(cliente)
    }
}