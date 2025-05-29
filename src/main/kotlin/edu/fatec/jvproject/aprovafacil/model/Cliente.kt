package edu.fatec.jvproject.aprovafacil.model

import edu.fatec.jvproject.aprovafacil.enum.EstadoCivil
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "clientes", uniqueConstraints = [UniqueConstraint(columnNames = ["cpf"])])
class Cliente(
    var nome: String,
    @Column(unique = true)
    var cpf: String,
    var telefone: String,
    var email: String,
    var dataNascimento: LocalDate,
    var statusCadastro: StatusCliente,
    var estadoCivil: EstadoCivil,

    @Embedded
    var perfilFinanceiro: PerfilFinanceiro,
    @Embedded
    var dadosInteresse: DadosInteresse,

    @OneToMany(mappedBy = "cliente", cascade = [CascadeType.ALL], orphanRemoval = true)
    var documentos: MutableList<DocumentoCliente> = mutableListOf(),

    @Column(nullable = true)
    var devolutiva: String? = null,

    @ManyToOne
    @JoinColumn(name = "id_participante")
    var participante: Cliente? = null

) : EntidadeDominio()
