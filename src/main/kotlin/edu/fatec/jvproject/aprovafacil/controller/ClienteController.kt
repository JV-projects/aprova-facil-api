package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.BuscarCpfRequest
import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.mapper.ClienteMapper
import edu.fatec.jvproject.aprovafacil.service.IClienteService
import edu.fatec.jvproject.aprovafacil.service.IDocumentoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteService: IClienteService, private val documentoService: IDocumentoService) {

    @PostMapping("/salvar")
    fun salvarCliente(@Valid @RequestBody clienteDto: ClienteDto): ResponseEntity<ClienteDto> {

        var clienteSalvo = ClienteMapper().from(clienteService.salvarClienteComInformacoes(clienteDto))

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @PostMapping("/buscarPorCpf")
            /** Mudei pra @RequestBody porque não é seguro cpf ficar visível na URL**/
    fun buscarClientePeloCpf(@RequestBody request: BuscarCpfRequest): ResponseEntity<ClienteDto> {
        val cliente = clienteService.buscarClientePeloCpf(request.cpf)
        return ResponseEntity.ok(ClienteMapper().from(cliente))
    }

    @GetMapping("/buscarPorId")
    fun buscarClientePorId(@RequestParam("id") idCliente: Long): ResponseEntity<ClienteDto> {
        val cliente = clienteService.buscarClientePeloId(idCliente)
        return ResponseEntity.ok(ClienteMapper().from(cliente))
    }

    @GetMapping("/buscarPorStatus")
    fun buscarClientePeloStatus(@RequestParam("status") status: StatusCliente): ResponseEntity<List<ClienteDto>> {
        val clientes = clienteService.buscarClientesPeloStatus(status)
        return ResponseEntity.ok(ClienteMapper().mapFromList(clientes))
    }

    @GetMapping("/listar")
    fun listarClientes(): ResponseEntity<List<ClienteDto>> {

        return ResponseEntity.ok(ClienteMapper().mapFromList(clienteService.listarClientes()))
    }

    @PutMapping("/atualizar")
    fun atualizarCliente(@Valid @RequestBody dto: ClienteDto): ResponseEntity<ClienteDto> {
        var clienteAtualizado = clienteService.atualizarCliente(dto)

        return ResponseEntity.ok(ClienteMapper().from(clienteAtualizado))
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarCliente(@PathVariable id: Long): ResponseEntity<Unit> {
        clienteService.deletarCliente(id)
        return ResponseEntity.noContent().build()
    }

}