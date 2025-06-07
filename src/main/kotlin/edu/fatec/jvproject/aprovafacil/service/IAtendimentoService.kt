package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.DistribuirRequest
import edu.fatec.jvproject.aprovafacil.model.Atendimento

interface IAtendimentoService {
    fun registrarAtendimento(atendimento: AtendimentoDTO) : Atendimento
    fun distribuirCliente(distribuirRequest: DistribuirRequest)
    fun registrarDevolutiva(codigo: String, devolutiva: String) : Atendimento
    fun buscarAtendimentoPorCliente(idCliente: Long) : Atendimento?
    fun atualizarAtendimento(atendimento : Atendimento) : Atendimento
}