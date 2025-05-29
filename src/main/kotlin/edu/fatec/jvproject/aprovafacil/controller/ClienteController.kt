package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.BuscarCpfRequest
import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.enum.TipoDocumento
import edu.fatec.jvproject.aprovafacil.exceptions.DocumentoException
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
        var clienteSalvo = ClienteMapper().from(clienteService.salvarClienteComInformacoes(dto))

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
        validarEAdicionar(TipoDocumento.RG, rg, documentosMap)
        validarEAdicionar(TipoDocumento.CPF, cpf, documentosMap)
        validarEAdicionar(TipoDocumento.COMPROVANTE_RESIDENCIA, comprovanteResidencia, documentosMap)
        validarEAdicionar(TipoDocumento.PIS_CARTAO_CIDADAO, pisCartao, documentosMap)
        validarEAdicionar(TipoDocumento.CERTIDAO, certidao, documentosMap)
        validarEAdicionar(TipoDocumento.HOLERITE, holerite, documentosMap)
        validarEAdicionar(TipoDocumento.EXTRATO_FGTS, extratoFgts, documentosMap)
        validarEAdicionar(TipoDocumento.DECLARACAO_IR, declaracaoIr, documentosMap)
        validarEAdicionar(TipoDocumento.CARTEIRA_TRABALHO, carteiraTrabalho, documentosMap)

        documentoService.processarMapDocumentos(documentosMap, clienteId)
        return ResponseEntity.status(HttpStatus.CREATED).body("Documentos carregados com sucesso.");
    }

    @GetMapping("/recuperarDocumentos")
    fun listarDocumentosPorCliente(@RequestParam id: Long): ResponseEntity<Map<TipoDocumento, String>> {
        val documentos = documentoService.recuperarDocumentosDoClientePorId(id)
        return ResponseEntity.ok(documentos)
    }

    fun validarEAdicionar(
        tipo: TipoDocumento,
        arquivo: MultipartFile?,
        map: MutableMap<TipoDocumento, MultipartFile>
    ) {
        arquivo?.let {
            if (!isPdfPelaExtensao(it)) throw DocumentoException("Formato de arquivo inválido para ${tipo.name}, deve ser .pdf")
            map[tipo] = it
        }
    }

    fun isPdfPelaExtensao(file: MultipartFile): Boolean {
        return file.originalFilename?.lowercase()?.endsWith(".pdf") == true
    }


}