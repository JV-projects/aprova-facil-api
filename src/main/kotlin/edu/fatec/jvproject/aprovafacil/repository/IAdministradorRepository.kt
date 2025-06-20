package edu.fatec.jvproject.aprovafacil.repository

import edu.fatec.jvproject.aprovafacil.model.Administrador
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IAdministradorRepository : JpaRepository<Administrador, Long> {
    fun findByEmail(email: String): Administrador?
}