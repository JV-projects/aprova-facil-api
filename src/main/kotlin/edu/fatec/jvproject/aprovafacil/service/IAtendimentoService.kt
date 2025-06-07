package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.AtendimentoRequest
import edu.fatec.jvproject.aprovafacil.model.Atendimento

interface IAtendimentoService {
    fun registrarAnaliseCredito(atendimentoRequest: AtendimentoRequest) : AtendimentoDTO
    fun distribuirCliente(atendimentoRequest: AtendimentoRequest) : String
    fun registrarDevolutiva(codigo: String, devolutiva: String) : Atendimento
    fun buscarAtendimentoPorCliente(idCliente: Long) : Atendimento?
}