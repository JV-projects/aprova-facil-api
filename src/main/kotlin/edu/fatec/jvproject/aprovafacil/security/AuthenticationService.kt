package edu.fatec.jvproject.aprovafacil.security

import edu.fatec.jvproject.aprovafacil.exceptions.UsernameNaoEncontradoException
import edu.fatec.jvproject.aprovafacil.repository.IAdministradorRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    val repository : IAdministradorRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        return repository.findByEmail(username)
            ?: throw UsernameNaoEncontradoException("Usuário com email $username não encontrado")
    }

}