package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.CredenciaisDto
import edu.fatec.jvproject.aprovafacil.model.Administrador
import edu.fatec.jvproject.aprovafacil.service.AutenticacaoTokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AutenticacaoController(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: AutenticacaoTokenService
) {
    @PostMapping("/login")
    fun autenticarUsuario(@RequestBody credenciaisDto: CredenciaisDto): ResponseEntity<Map<String, String>> {
        var usernameSenha = UsernamePasswordAuthenticationToken(credenciaisDto.email, credenciaisDto.senha)
        var auth = this.authenticationManager.authenticate(usernameSenha)

        var token = tokenService.generateToken(auth.principal as Administrador)

        var response = mapOf("token" to token)
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}