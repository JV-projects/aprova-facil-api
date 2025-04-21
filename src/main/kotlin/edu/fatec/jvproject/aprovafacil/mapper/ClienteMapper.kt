package edu.fatec.jvproject.aprovafacil.mapper

import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.model.Cliente

class ClienteMapper : AbstractMapper<ClienteDto, Cliente>() {
    override fun to(from: ClienteDto): Cliente {
        return Cliente(
            nome = from.nome,
            cpf = from.cpf,
            telefone = from.telefone,
            email = from.email,
            dataNascimento = from.dataNascimento,
            statusCadastro = StatusCliente.PENDENTE,
            perfilFinanceiro = from.perfilFinanceiro,
            dadosInteresse = from.dadosInteresse
        )
    }

    override fun from(to: Cliente): ClienteDto {
        return ClienteDto(
            nome = to.nome,
            cpf = to.cpf,
            telefone = to.telefone,
            email = to.email,
            dataNascimento = to.dataNascimento,
            status = to.statusCadastro,
            perfilFinanceiro = to.perfilFinanceiro,
            dadosInteresse = to.dadosInteresse
        )
    }
}