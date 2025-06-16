package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.AtendimentoDTO
import edu.fatec.jvproject.aprovafacil.dto.ClienteDevolutivaDTO
import edu.fatec.jvproject.aprovafacil.dto.DevolutivaRequest
import edu.fatec.jvproject.aprovafacil.exceptions.TokenException
import edu.fatec.jvproject.aprovafacil.mapper.toDto
import edu.fatec.jvproject.aprovafacil.service.IAtendimentoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/corretor")
class CorretorController(
    private val atendimentoService: IAtendimentoService
) {

    @PostMapping("/validarCodigoDevolutiva")
    fun validarCodigoDevolutiva(@RequestParam codigoDevolutiva: String): ResponseEntity<ClienteDevolutivaDTO> {
        val clienteDevolutiva = atendimentoService.verificarCodigoDevolutiva(codigoDevolutiva)

        return ResponseEntity.ok(clienteDevolutiva)
    }

    @PostMapping("/devolutiva")
    fun registrarDevolutiva(@RequestBody devolutivaRequest: DevolutivaRequest): ResponseEntity<AtendimentoDTO> {
        val atendimento = atendimentoService.registrarDevolutiva(
            devolutivaRequest
        )

        return ResponseEntity.ok(atendimento.toDto())
    }
}