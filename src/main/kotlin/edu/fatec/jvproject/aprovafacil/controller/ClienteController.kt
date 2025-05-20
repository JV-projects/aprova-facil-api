package edu.fatec.jvproject.aprovafacil.controller

import edu.fatec.jvproject.aprovafacil.dto.BaseResponseEntity
import edu.fatec.jvproject.aprovafacil.dto.ClienteDto
import edu.fatec.jvproject.aprovafacil.dto.DataResponseEntity
import edu.fatec.jvproject.aprovafacil.dto.ErroResponseEntity
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
import java.time.LocalDateTime

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteService: IClienteService, private val documentoService: IDocumentoService) {

    @PostMapping("/salvar")
    fun salvarCliente(@Valid @RequestBody dto: ClienteDto): ResponseEntity<out BaseResponseEntity> {
        var cliente = ClienteMapper().to(dto)
        var clienteSalvo = clienteService.salvarClienteComInformacoes(cliente)

        var data = DataResponseEntity(
            data = ClienteMapper().from(clienteSalvo),
            timestamp = LocalDateTime.now(),
            status = HttpStatus.CREATED.value(),
            mensagem = "Cadastrado feito com sucesso."
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @GetMapping("/buscarPorCpf")
    fun buscarClientePeloCpf(@RequestParam("cpf") cpfCliente: String): ResponseEntity<out BaseResponseEntity> {
        val cliente = clienteService.buscarClientePeloCpf(cpfCliente)

        return if (cliente == null) {
            var erro = ErroResponseEntity(
                timestamp = LocalDateTime.now(),
                status = HttpStatus.NOT_FOUND.value(),
                mensagem = "Cliente não encontrado para o cpf: $cpfCliente informado."
            )
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro)
        } else {
            var data = DataResponseEntity(
                data = ClienteMapper().from(cliente),
                timestamp = LocalDateTime.now(),
                status = HttpStatus.OK.value(),
                mensagem = ""
            )

            ResponseEntity.ok(data)
        }
    }

    @GetMapping("/buscarPorId")
    fun buscarClientePorId(@RequestParam("id") idCliente: Long): ResponseEntity<out BaseResponseEntity> {
        val cliente = clienteService.buscarClientePeloId(idCliente)

        return if (cliente == null) {
            var erro = ErroResponseEntity(
                timestamp = LocalDateTime.now(),
                status = HttpStatus.NOT_FOUND.value(),
                mensagem = "Cliente não encontrado para o id: $idCliente informado."
            )
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro)
        } else {
            var data = DataResponseEntity(
                data = ClienteMapper().from(cliente),
                timestamp = LocalDateTime.now(),
                status = HttpStatus.OK.value(),
                mensagem = ""
            )

            ResponseEntity.ok(data)
        }
    }

    @GetMapping("/buscarPorStatus")
    fun buscarClientePeloStatus(@RequestParam("status") status: StatusCliente): ResponseEntity<out BaseResponseEntity> {
        val clientes = clienteService.buscarClientesPeloStatus(status)

        var data = DataResponseEntity(
            data = ClienteMapper().mapFromList(clientes),
            timestamp = LocalDateTime.now(),
            status = HttpStatus.OK.value(),
            mensagem = ""
        )

        return ResponseEntity.ok(data)
    }

    @GetMapping("/listar")
    fun listarClientes(): ResponseEntity<out BaseResponseEntity> {

        var data = DataResponseEntity(
            data = ClienteMapper().mapFromList(clienteService.listarClientes()),
            timestamp = LocalDateTime.now(),
            status = HttpStatus.OK.value(),
            mensagem = ""
        )

        return ResponseEntity.ok(data)
    }

    @PutMapping("/atualizar")
    fun atualizarCliente(@Valid @RequestBody dto: ClienteDto): ResponseEntity<out BaseResponseEntity> {
        var cliente = ClienteMapper().to(dto)
        var clienteAtualizado = clienteService.atualizarCliente(cliente)

        var data = DataResponseEntity(
            data = ClienteMapper().from(clienteAtualizado),
            timestamp = LocalDateTime.now(),
            status = HttpStatus.OK.value(),
            mensagem = "Atualização feita com sucesso."
        )
        return ResponseEntity.ok(data)
    }

    @DeleteMapping("/deletar/{id}")
    fun deletarCliente(@PathVariable id: Long): ResponseEntity<Unit> {
        clienteService.deletarCliente(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/documentos")
    fun uploadDocumentos(  @RequestParam("clienteId") clienteId: Long,
                                 @RequestParam("RG") rg: MultipartFile?,
                                 @RequestParam("CPF") cpf: MultipartFile?,
                                 @RequestParam("CERTIDAO") certidao: MultipartFile?,
                                 @RequestParam("HOLERITE") holerite: MultipartFile?,
                                 @RequestParam("EXTRATO_FGTS") extratoFgts: MultipartFile?,
                                 @RequestParam("CARTEIRA_TRABALHO") carteiraTrabalho: MultipartFile?): ResponseEntity<out BaseResponseEntity> {

        val documentosMap = mutableMapOf<TipoDocumento, MultipartFile>()
        rg?.let { documentosMap[TipoDocumento.RG] = it }
        cpf?.let { documentosMap[TipoDocumento.CPF] = it }
        certidao?.let { documentosMap[TipoDocumento.CERTIDAO] = it }
        holerite?.let { documentosMap[TipoDocumento.HOLERITE] = it }
        extratoFgts?.let { documentosMap[TipoDocumento.EXTRATO_FGTS] = it }
        carteiraTrabalho?.let { documentosMap[TipoDocumento.CARTEIRA_TRABALHO] = it }

        documentoService.processarMapDocumentos(documentosMap, clienteId)

        var data = DataResponseEntity(
            data = null,
            timestamp = LocalDateTime.now(),
            status = HttpStatus.CREATED.value(),
            mensagem = "Documentos carregados com sucesso."
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }
}