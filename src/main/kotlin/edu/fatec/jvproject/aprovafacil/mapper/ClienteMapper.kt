package edu.fatec.jvproject.aprovafacil.mapper

import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.dto.DadosInteresseDto
import edu.fatec.jvproject.aprovafacil.dto.PerfilFinanceiroDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse
import edu.fatec.jvproject.aprovafacil.model.PerfilFinanceiro

class ClienteMapper : AbstractMapper<ClienteDto, Cliente>() {
    override fun to(from: ClienteDto): Cliente {
        return Cliente(
            nome = from.nome,
            cpf = from.cpf,
            telefone = from.telefone,
            email = from.email,
            dataNascimento = from.dataNascimento,
            statusCadastro = StatusCliente.PENDENTE,
            perfilFinanceiro = PerfilFinanceiro(
                from.perfilFinanceiro.rendaBruta,
                from.perfilFinanceiro.tipoRenda,
                from.perfilFinanceiro.possuiRestricao,
                from.perfilFinanceiro.possuiDependente,
                from.perfilFinanceiro.tresAnosFgts,
                from.perfilFinanceiro.desejaUsarFgts
            ),
            dadosInteresse = DadosInteresse(
                from.dadosInteresse.tipoImovel,
                from.dadosInteresse.regiaoInteresse
            )
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
            perfilFinanceiro = PerfilFinanceiroDto(
                to.perfilFinanceiro.rendaBruta,
                to.perfilFinanceiro.tipoRenda,
                to.perfilFinanceiro.temRestricaoCredito,
                to.perfilFinanceiro.temDependente,
                to.perfilFinanceiro.tresAnosFgts,
                to.perfilFinanceiro.desejaUsarFgts

            ),
            dadosInteresse = DadosInteresseDto(
                to.dadosInteresse.tipoImovel,
                to.dadosInteresse.regiaoInteresse
            )
        )
    }
}