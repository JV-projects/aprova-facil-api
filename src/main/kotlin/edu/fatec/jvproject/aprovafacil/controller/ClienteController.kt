package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.dto.ClienteRequestDto
import edu.fatec.jvproject.aprovafacil.mapper.ClienteMapper
import edu.fatec.jvproject.aprovafacil.service.ClienteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteService: ClienteService) {

    @PostMapping("/salvar")
    fun salvarCliente(@Valid @RequestBody dto: ClienteDto): ResponseEntity<ClienteDto> {
        var cliente = ClienteMapper().to(dto)
        var clienteSalvo = clienteService.salvarClienteComInformacoes(cliente)

        return ResponseEntity.ok(ClienteMapper().from(clienteSalvo))
    }

    @PostMapping("/buscar")
    fun buscarClientePeloCpf(@Valid @RequestBody clienteRequest: ClienteRequestDto): ResponseEntity<Any> {
        val cliente = clienteService.buscarClientePeloCpf(clienteRequest.cpf)

        return if (cliente == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado com CPF: ${clienteRequest.cpf}")
        } else {
            ResponseEntity.ok(ClienteMapper().from(cliente))
        }
    }

    @GetMapping("/listar")
    fun listarClientes(): ResponseEntity<List<ClienteDto>> {
        return ResponseEntity.ok(ClienteMapper().mapFromList(clienteService.listarClientes()))
    }

    @PutMapping("/atualizar")
    fun atualizarCliente(@Valid @RequestBody dto: ClienteDto): ResponseEntity<Any> {
        var cliente = ClienteMapper().to(dto)
        clienteService.atualizarCliente(cliente)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarCliente(@PathVariable id: Long): ResponseEntity<Any> {
        clienteService.deletarCliente(id)
        return ResponseEntity.ok().build()
    }
}