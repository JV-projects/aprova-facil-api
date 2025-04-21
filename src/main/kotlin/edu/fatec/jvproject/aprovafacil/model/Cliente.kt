package edu.fatec.jvproject.aprovafacil.model

import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "clientes", uniqueConstraints = [UniqueConstraint(columnNames = ["cpf"])])
data class Cliente(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    @Column(unique = true)
    val cpf: String,
    val telefone: String,
    val email: String,
    val dataNascimento: LocalDate,
    val statusCadastro: StatusCliente,

    @Embedded
    val perfilFinanceiro: PerfilFinanceiro,
    @Embedded
    val dadosInteresse: DadosInteresse
)
