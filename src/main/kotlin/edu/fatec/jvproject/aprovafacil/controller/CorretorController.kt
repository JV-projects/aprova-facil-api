package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.DevolutivaRequest
import edu.fatec.jvproject.aprovafacil.mapper.toDto
import edu.fatec.jvproject.aprovafacil.model.Atendimento
import edu.fatec.jvproject.aprovafacil.service.AtendimentoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/corretor")
class CorretorController(
    private val atendimentoService: AtendimentoService
) {

    @PostMapping("/devolutiva")
    fun registrarDevolutiva(@RequestBody devolutivaRequest: DevolutivaRequest) : ResponseEntity<AtendimentoDTO>{
       val atendimento =  atendimentoService.registrarDevolutiva(
            devolutivaRequest.codigo,
            devolutivaRequest.devolutiva)

        return ResponseEntity.ok(atendimento.toDto())
    }
}