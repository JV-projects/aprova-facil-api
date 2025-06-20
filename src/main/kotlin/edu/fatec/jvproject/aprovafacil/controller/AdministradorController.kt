package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.AtendimentoRequest
import edu.fatec.jvproject.aprovafacil.service.IAtendimentoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/administrador")
class AdministradorController(
    private val atendimentoService: IAtendimentoService
) {
    @PostMapping("/analise")
    fun registrarAnaliseCreditoCliente(@RequestBody request: AtendimentoRequest): ResponseEntity<AtendimentoDTO> {
        val atendimento = atendimentoService.registrarAnaliseCredito(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimento)
    }

    @PostMapping("/distribuir")
    fun distribuir(@RequestBody request: AtendimentoRequest): ResponseEntity<Map<String, String>> {
        val codigo = atendimentoService.distribuirCliente(request)
        val response = mapOf("codigoDevolutiva" to codigo)
        return ResponseEntity.ok(response)
    }

}