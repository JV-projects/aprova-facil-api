package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.model.Cliente
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.Date

@Service
class DevolutivaTokenService(
    @Value("\${jwt.assinatura}")
    private val assinatura: String
) : ITokenService<Cliente> {

    private val chave = Keys.hmacShaKeyFor(
        MessageDigest.getInstance("SHA-256").digest(assinatura.toByteArray(Charsets.UTF_8))
    )
    override fun generateToken(objeto: Cliente): String {
        return Jwts.builder()
            .subject(objeto.id.toString())
            .issuedAt(Date())
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
            obterClaims(token)
            true
        } catch (ex: Exception) {
            false
        }
    }
}