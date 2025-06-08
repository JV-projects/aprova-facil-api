package edu.fatec.jvproject.aprovafacil.security

import edu.fatec.jvproject.aprovafacil.repository.IAdministradorRepository
import edu.fatec.jvproject.aprovafacil.service.AutenticacaoTokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter


@Service
class SecurityFilter(
    private val tokenService: AutenticacaoTokenService,
    private val authenticationService: AuthenticationService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwtToken = recuperarToken(request)

        if (jwtToken != null && tokenService.isTokenValid(jwtToken)) {
            val claims = tokenService.obterClaims(jwtToken)
            val email = claims["email"] as String

            val userDetails = authenticationService.loadUserByUsername(email)
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails?.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization") ?: return null
        return if (authHeader.startsWith("Bearer ")) authHeader.substring(7) else null
    }

}