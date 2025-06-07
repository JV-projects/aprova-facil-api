package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.DistribuirRequest
import edu.fatec.jvproject.aprovafacil.model.Atendimento
import edu.fatec.jvproject.aprovafacil.service.IAtendimentoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/adm")
class AdministradorController(
    private val atendimentoService: IAtendimentoService
) {
    @PostMapping("/analiseCredito")
    fun registrarAnaliseCreditoCliente(@RequestBody request: AtendimentoDTO): ResponseEntity<Atendimento> {
        val atendimento = atendimentoService.registrarAtendimento(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimento)
    }

    @PostMapping("/distribuir")
    fun distribuir(@RequestParam request: DistribuirRequest): ResponseEntity<String> {
        atendimentoService.distribuirCliente(request)
        return ResponseEntity.ok("Cliente encaminhado para atendimento com corretor.")
    }

}