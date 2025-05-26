package edu.fatec.jvproject.aprovafacil.dto

import edu.fatec.jvproject.aprovafacil.enum.EstadoCivil
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class ClienteDto(
    val id: Long? = null,
    @field:NotBlank(message = "Informe o 'nome'.")
    val nome: String,

    @field:NotBlank(message = "Informe o 'cpf'.")
    val cpf: String,

    @field:NotBlank(message = "Informe o 'telefone'.")
    val telefone: String,

    @field:NotBlank(message = "Informe o 'email'.")
    @field:Email(message = "O 'email' está com formato inválido.")
    val email: String,

    val status: StatusCliente? = null,
    val estadoCivil: EstadoCivil,

    @field:NotNull(message = "A 'data de nascimento' é obrigatória.")
    @field:Past(message = "Não é possível inserir uma 'data de nascimento' no futuro.")
    val dataNascimento: LocalDate,

    @field:Valid
    val perfilFinanceiro: PerfilFinanceiroDto,

    @field:Valid
    val dadosInteresse: DadosInteresseDto,

    val devolutiva: String? = null
)