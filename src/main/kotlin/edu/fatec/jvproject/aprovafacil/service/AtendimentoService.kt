package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.ConteudoTemplate
import edu.fatec.jvproject.aprovafacil.dto.AtendimentoRequest
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteException
import edu.fatec.jvproject.aprovafacil.exceptions.TokenException
import edu.fatec.jvproject.aprovafacil.mapper.toDto
import edu.fatec.jvproject.aprovafacil.model.Atendimento
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.repository.IAtendimentoRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class AtendimentoService(
    val clienteService: IClienteService,
    val atendimentoRepository: IAtendimentoRepository,
    val emailSender: EmailSenderService,
    val tokenService: DevolutivaTokenService
) : IAtendimentoService {
    override fun registrarAnaliseCredito(atendimentoRequest: AtendimentoRequest): AtendimentoDTO {
        val cliente = clienteService.buscarClientePeloId(atendimentoRequest.idCliente)
        val atendimento = salvarOuAtualizarAtendimento(cliente, AtendimentoDTO(
            idCliente = cliente.id!!,
            analiseCredito = atendimentoRequest.analiseCredito,
            emailCorretor = atendimentoRequest.emailCorretor
        ))
        clienteService.atualizarStatusCliente(cliente.id!!, StatusCliente.AGUARDANDO_DISTRIBUICAO)
        return atendimento.toDto()
    }

    override fun distribuirCliente(atendimentoRequest: AtendimentoRequest) : String{
        val cliente = clienteService.buscarClientePeloId(atendimentoRequest.idCliente)
        val atendimentoDTO = AtendimentoDTO(
            idCliente = cliente.id!!,
            analiseCredito = atendimentoRequest.analiseCredito,
            emailCorretor = atendimentoRequest.emailCorretor
        )

        val atendimento = salvarOuAtualizarAtendimento(cliente, atendimentoDTO)

        val codigoGerado = enviarEmailParaCorretor(cliente, atendimento)
        clienteService.atualizarStatusCliente(cliente.id!!, StatusCliente.EM_ATENDIMENTO)

        return codigoGerado
    }

    override fun registrarDevolutiva(codigo: String, devolutiva: String): Atendimento {
        if (!tokenService.isTokenValid(codigo)) {
            throw TokenException("Token inválido")
        }

        val clienteId = tokenService.obterClaims(codigo).subject.toLong()
        val cliente = clienteService.buscarClientePeloId(clienteId)
        val atendimento = buscarAtendimentoPorCliente(cliente.id!!)

        atendimento.devolutiva = devolutiva
        clienteService.atualizarStatusCliente(cliente.id!!, StatusCliente.ATENDIMENTO_CONCLUIDO)

        return atendimentoRepository.save(atendimento)
    }

    override fun buscarAtendimentoPorCliente(idCliente: Long): Atendimento {
        return atendimentoRepository.findByClienteId(idCliente)
            ?: throw ClienteException("Dados de atendimento não encontrados.")
    }

    private fun salvarOuAtualizarAtendimento(cliente: Cliente, dto: AtendimentoDTO): Atendimento {
        val atendimento = atendimentoRepository.findByClienteId(cliente.id!!)?.apply {
            analiseCredito = dto.analiseCredito
            emailCorretor = dto.emailCorretor
        } ?: Atendimento(
            cliente = cliente,
            analiseCredito = dto.analiseCredito,
            emailCorretor = dto.emailCorretor
        )

        return atendimentoRepository.save(atendimento)
    }

    private fun enviarEmailParaCorretor(cliente: Cliente, atendimento: Atendimento) : String{
        val codigo = tokenService.generateToken(cliente)

        val conteudo = ConteudoTemplate(
            clienteNome = cliente.nome,
            telefone = cliente.telefone,
            email = cliente.email,
            interesseImovel = cliente.dadosInteresse.tipoImovel,
            estadoImovel = cliente.dadosInteresse.estadoImovel,
            analiseCredito = atendimento.analiseCredito,
            codigoDevolutiva = codigo
        )

        emailSender.enviarEmail(conteudo, atendimento.emailCorretor)
        return codigo
    }
}