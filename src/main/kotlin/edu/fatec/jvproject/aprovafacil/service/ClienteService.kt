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
import java.time.LocalDate
import java.time.Period

@Service
class ClienteService(
    val clienteRepository: IClienteRepository,
    val documentoService: IDocumentoService
) : IClienteService {

    @Transactional
    override fun salvarClienteComInformacoes(clienteDto: ClienteDto): Cliente {

        validarMaioridade(clienteDto)

        val clienteEncontrado = clienteRepository.findByCpf(clienteDto.cpf)
        if (clienteEncontrado != null)
            throw ClienteException("CPF ${clienteDto.cpf} já cadastrado")

        val clienteASerSalvo = ClienteMapper().to(clienteDto)

        verificarExistenciaParticipante(clienteDto, clienteASerSalvo)

        if (clienteDto.documentos.isEmpty())
            throw DocumentoException("Nenhum documento foi enviado")

        val clienteSalvo = clienteRepository.save(clienteASerSalvo)

        val documentos = documentoService.processarDocumentosBase64(clienteDto.documentos, clienteSalvo)

        clienteSalvo.registroDocumentos.addAll(documentos)

        return clienteSalvo
    }

    private fun verificarExistenciaParticipante(
        clienteDto: ClienteDto,
        cliente: Cliente
    ) {
        val cpfParticipante = clienteDto.participante

        if (cpfParticipante.isNullOrBlank()) {
            cliente.participante = null
        } else {
            val participante = clienteRepository.findByCpf(cpfParticipante)
                ?: throw ClienteException("Participante não encontrado para o CPF $cpfParticipante")
            cliente.participante = participante
        }
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
        if (clienteEncontrado != null && clienteEncontrado.id != clienteDto.id) {
            throw ClienteException("CPF ${clienteDto.cpf} já cadastrado.")
        }

        val clienteExistente = clienteRepository.findById(clienteDto.id)
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

        verificarExistenciaParticipante(clienteDto, clienteExistente)

        if (clienteDto.documentos.isNotEmpty()) {
            val documentosAtualizados = documentoService.processarDocumentosParaAtualizacao(clienteDto.documentos, clienteExistente)

            val mapaDocumentosExistentes = clienteExistente.registroDocumentos.associateBy { it.tipoDocumento }.toMutableMap()

            documentosAtualizados.forEach { novoDoc ->
                mapaDocumentosExistentes[novoDoc.tipoDocumento] = novoDoc
            }

            clienteExistente.registroDocumentos.clear()
            clienteExistente.registroDocumentos.addAll(mapaDocumentosExistentes.values)
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

    private fun validarMaioridade(clienteDto: ClienteDto) {
        val hoje = LocalDate.now()
        val dataNascimento = clienteDto.dataNascimento

        val data18Anos = dataNascimento.plusYears(18)

        if (hoje.isBefore(data18Anos)) {
            val idadeAproximada = Period.between(dataNascimento, hoje).years
            throw ClienteException("Cliente deve ter pelo menos 18 anos. Idade atual: $idadeAproximada")
        }
    }

}