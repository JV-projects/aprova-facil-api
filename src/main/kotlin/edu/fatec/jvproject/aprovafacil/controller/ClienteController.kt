package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.BuscarCpfRequest
import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.enum.TipoDocumento
import edu.fatec.jvproject.aprovafacil.mapper.ClienteMapper
import edu.fatec.jvproject.aprovafacil.service.IClienteService
import edu.fatec.jvproject.aprovafacil.service.IDocumentoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteService: IClienteService, private val documentoService: IDocumentoService) {

    @PostMapping("/salvar")
    fun salvarCliente(@Valid @RequestBody dto: ClienteDto): ResponseEntity<ClienteDto> {
        var cliente = ClienteMapper().to(dto)
        var clienteSalvo = ClienteMapper().from(clienteService.salvarClienteComInformacoes(cliente))

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
        var cliente = ClienteMapper().to(dto)
        var clienteAtualizado = clienteService.atualizarCliente(cliente)

        return ResponseEntity.ok(ClienteMapper().from(clienteAtualizado))
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarCliente(@PathVariable id: Long): ResponseEntity<Unit> {
        clienteService.deletarCliente(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/documentos")
    fun uploadDocumentos(
        @RequestParam("clienteId") clienteId: Long,
        @RequestParam("RG") rg: MultipartFile?,
        @RequestParam("CPF") cpf: MultipartFile?,
        @RequestParam("COMPROVANTE_RESIDENCIA") comprovanteResidencia: MultipartFile?,
        @RequestParam("PIS_CARTAO_CIDADAO") pisCartao: MultipartFile?,
        @RequestParam("CERTIDAO") certidao: MultipartFile?,
        @RequestParam("HOLERITE") holerite: MultipartFile?,
        @RequestParam("EXTRATO_FGTS") extratoFgts: MultipartFile?,
        @RequestParam("DECLARACAO_IR") declaracaoIr: MultipartFile?,
        @RequestParam("CARTEIRA_TRABALHO") carteiraTrabalho: MultipartFile?
    ): ResponseEntity<String> {

        val documentosMap = mutableMapOf<TipoDocumento, MultipartFile>()
        rg?.let { documentosMap[TipoDocumento.RG] = it }
        cpf?.let { documentosMap[TipoDocumento.CPF] = it }
        comprovanteResidencia?.let { documentosMap[TipoDocumento.COMPROVANTE_RESIDENCIA] = it }
        pisCartao?.let { documentosMap[TipoDocumento.PIS_CARTAO_CIDADAO] = it }
        certidao?.let { documentosMap[TipoDocumento.CERTIDAO] = it }
        holerite?.let { documentosMap[TipoDocumento.HOLERITE] = it }
        extratoFgts?.let { documentosMap[TipoDocumento.EXTRATO_FGTS] = it }
        declaracaoIr?.let { documentosMap[TipoDocumento.DECLARACAO_IR] = it }
        carteiraTrabalho?.let { documentosMap[TipoDocumento.CARTEIRA_TRABALHO] = it }

        documentoService.processarMapDocumentos(documentosMap, clienteId)
        return ResponseEntity.status(HttpStatus.CREATED).body("Documentos carregados com sucesso.");
    }
}