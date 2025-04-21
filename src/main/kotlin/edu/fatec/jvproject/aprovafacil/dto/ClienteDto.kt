package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse
import edu.fatec.jvproject.aprovafacil.model.PerfilFinanceiro
import java.time.LocalDate

data class ClienteDto(
    val nome: String,
    val cpf: String,
    val telefone: String,
    val email: String,
    val status: StatusCliente? = null,
    val dataNascimento: LocalDate,
    val perfilFinanceiro: PerfilFinanceiro,
    val dadosInteresse: DadosInteresse
)