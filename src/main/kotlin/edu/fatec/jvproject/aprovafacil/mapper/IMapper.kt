package edu.fatec.jvproject.aprovafacil.mapper

interface IMapper <From, To> {
    fun to(from: From): To
    fun from(to: To): From
}