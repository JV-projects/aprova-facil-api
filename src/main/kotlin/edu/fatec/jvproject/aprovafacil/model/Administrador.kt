package edu.fatec.jvproject.aprovafacil.model

import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "administradores")
class Administrador(
    val nome: String,
    val email: String,
    val senha: String,
) : EntidadeDominio(), UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
    }

    override fun getPassword(): String? {
        return senha
    }

    override fun getUsername(): String? {
        return email
    }
}