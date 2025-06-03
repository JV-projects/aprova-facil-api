package edu.fatec.jvproject.aprovafacil.model

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "atendimentos")
class Atendimento(
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    var cliente: Cliente,
    var analiseCredito: String,
    var emailCorretor: String,
    var devolutiva: String? = null
) : EntidadeDominio()