package edu.fatec.jvproject.aprovafacil.mapper

import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.dto.DadosInteresseDto
import edu.fatec.jvproject.aprovafacil.dto.PerfilFinanceiroDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.exceptions.ClienteNaoEncontradoException
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse
import edu.fatec.jvproject.aprovafacil.model.PerfilFinanceiro
import edu.fatec.jvproject.aprovafacil.service.IClienteService

class ClienteMapper() : AbstractMapper<ClienteDto, Cliente>() {
    override fun to(from: ClienteDto): Cliente {
        return Cliente(
            nome = from.nome,
            cpf = from.cpf,
            telefone = from.telefone,
            email = from.email,
            dataNascimento = from.dataNascimento,
            statusCadastro = StatusCliente.PENDENTE,
            estadoCivil = from.estadoCivil,
            perfilFinanceiro = PerfilFinanceiro(
                from.perfilFinanceiro.rendaBruta,
                from.perfilFinanceiro.tipoRenda,
                from.perfilFinanceiro.possuiRestricao,
                from.perfilFinanceiro.possuiDependente,
                from.perfilFinanceiro.tresAnosFgts,
                from.perfilFinanceiro.usarFgts
            ),
            dadosInteresse = DadosInteresse(
                from.dadosInteresse.tipoImovel,
                from.dadosInteresse.estadoImovel
            ),
            participante = null
        ).apply {
            from.id?.let { this.id = it }
        }
    }

    override fun from(to: Cliente): ClienteDto {
        return ClienteDto(
            id = to.id,
            nome = to.nome,
            cpf = to.cpf,
            telefone = to.telefone,
            email = to.email,
            dataNascimento = to.dataNascimento,
            status = to.statusCadastro,
            estadoCivil = to.estadoCivil,
            perfilFinanceiro = PerfilFinanceiroDto(
                to.perfilFinanceiro.rendaBruta,
                to.perfilFinanceiro.tipoRenda,
                to.perfilFinanceiro.possuiRestricao,
                to.perfilFinanceiro.possuiDependente,
                to.perfilFinanceiro.tresAnosFgts,
                to.perfilFinanceiro.usarFgts
            ),
            dadosInteresse = DadosInteresseDto(
                to.dadosInteresse.tipoImovel,
                to.dadosInteresse.estadoImovel
            ),
            participante = to.participante?.cpf ?: ""
        )
    }
}