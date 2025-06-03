package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.ConteudoTemplate
import edu.fatec.jvproject.aprovafacil.dto.DistribuirRequest
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteException
import edu.fatec.jvproject.aprovafacil.exceptions.TokenException
import edu.fatec.jvproject.aprovafacil.model.Atendimento
import edu.fatec.jvproject.aprovafacil.repository.IAtendimentoRepository
import org.springframework.stereotype.Service

@Service
class AtendimentoService(
    val clienteService: IClienteService,
    val atendimentoRepository: IAtendimentoRepository,
    val emailSender: EmailSenderService,
    val tokenService: DevolutivaTokenService
) : IAtendimentoService {
    override fun registrarAtendimento(atendimento: AtendimentoDTO) : Atendimento {
        var cliente = clienteService.buscarClientePeloId(atendimento.idCliente)
        var atendimento = Atendimento(
            cliente,
            atendimento.analiseCredito,
            atendimento.emailCorretor
        )

        clienteService.atualizarStatusCliente(cliente.id!!, StatusCliente.AGUARDANDO_DISTRIBUICAO)
        return atendimentoRepository.save(atendimento)
    }

    override fun distribuirCliente(distribuirRequest: DistribuirRequest) {
        val cliente = clienteService.buscarClientePeloId(distribuirRequest.idCliente)

        val atendimento = buscarAtendimentoPorCliente(cliente.id!!)
        atendimento.analiseCredito = distribuirRequest.analiseCredito
        atendimento.emailCorretor = distribuirRequest.emailCorretor
        atualizarAtendimento(atendimento)

        val conteudoTemplate = ConteudoTemplate(
            cliente.nome,
            cliente.telefone,
            cliente.email,
            cliente.dadosInteresse.tipoImovel,
            cliente.dadosInteresse.estadoImovel,
            atendimento.analiseCredito,
            tokenService.generateToken(cliente)
        )

        emailSender.enviarEmail(conteudoTemplate, atendimento.emailCorretor)
        clienteService.atualizarStatusCliente(cliente.id!!, StatusCliente.EM_ATENDIMENTO)
    }

    override fun registrarDevolutiva(codigo: String, devolutiva: String) : Atendimento{
        val isValid = tokenService.isTokenValid(codigo)

        if (!isValid)
            throw TokenException("Token inválido")

        val claims = tokenService.obterClaims(codigo)

        val clienteId = claims.subject.toLong()

        val cliente = clienteService.buscarClientePeloId(clienteId)
        val atendimento = buscarAtendimentoPorCliente(cliente.id!!)

        clienteService.atualizarStatusCliente(cliente.id!!, StatusCliente.ATENDIMENTO_CONCLUIDO)

        atendimento.devolutiva = devolutiva
        return atualizarAtendimento(atendimento)
    }

    override fun atualizarAtendimento(atendimento: Atendimento) : Atendimento{
        val atendimentoExistente = atendimentoRepository.findById(atendimento.id!!)

        atendimentoExistente.apply {
            atendimento.cliente
            atendimento.emailCorretor
            atendimento.analiseCredito
            atendimento.devolutiva
        }

        return atendimentoRepository.save(atendimento)
    }

    override fun buscarAtendimentoPorCliente(idCliente: Long): Atendimento {
        val atendimento = atendimentoRepository.findByCliente_Id(idCliente)
            ?: throw ClienteException("Dados de atendimento não encontrados.")

        return atendimento
    }
}