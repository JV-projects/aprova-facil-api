package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.model.Administrador
import edu.fatec.jvproject.aprovafacil.model.Cliente
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.apache.tomcat.websocket.AuthenticationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.*

@Service
class AutenticacaoTokenService(
    @Value("\${jwt.assinatura}")
    private val assinatura: String,
    @Value("\${jwt.expiracao}")
    private val expiracao: Long
) : ITokenService<Administrador> {

    private val chave = Keys.hmacShaKeyFor(
        MessageDigest.getInstance("SHA-256").digest(assinatura.toByteArray(Charsets.UTF_8))
    )

    override fun generateToken(objeto: Administrador): String {
        return Jwts.builder()
            .claim("nome", objeto.nome)
            .claim("email", objeto.email)
            .subject(objeto.id.toString())
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expiracao))
            .signWith(chave)
            .compact()
    }

    override fun obterClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(chave)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    override fun isTokenValid(token: String): Boolean {
        return try {
            val claims = obterClaims(token)
            val expiration = claims.expiration
            expiration.after(Date())
        } catch (ex: Exception) {
            throw AuthenticationException("Token inválido ou expirado")
        }
    }

}