package edu.fatec.jvproject.aprovafacil.service

import io.jsonwebtoken.Claims

interface ITokenService<T> {
    fun generateToken(objeto: T) : String
    fun obterClaims(token: String) : Claims
    fun isTokenValid(token: String) : Boolean
}