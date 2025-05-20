package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.enum.TipoDocumento
import edu.fatec.jvproject.aprovafacil.exceptions.DocumentoExistenteException
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DocumentoCliente
import edu.fatec.jvproject.aprovafacil.repository.IClienteRepository
import edu.fatec.jvproject.aprovafacil.repository.IDocumentoClienteRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@Service
class DocumentoService(
    val clienteRepository: IClienteRepository,
    val documentoRepository: IDocumentoClienteRepository
) : IDocumentoService {
    override fun processarMapDocumentos(documentos: Map<TipoDocumento, MultipartFile>, clienteId: Long) {
        val cliente = clienteRepository.findById(clienteId)
            .orElseThrow { RuntimeException("Cliente não encontrado") }

        validarExistenciaDocumentos(documentos, cliente)

        documentos.forEach { (tipo, arquivo) ->
            val arquivoArmazenado = armazenarDocumento(arquivo, tipo, cliente)
            registrarDocumento(
                DocumentoCliente(
                    arquivoArmazenado,
                    tipo,
                    cliente
                )
            )
        }
    }

    override fun armazenarDocumento(
        documento: MultipartFile,
        tipo: TipoDocumento,
        cliente: Cliente
    ): String {

        val baseDir = Paths.get("").toAbsolutePath().resolve("documentos")
        val nomeArquivo = "${cliente.id}_${tipo.name.uppercase()}"
        val pathDestino = baseDir.resolve(nomeArquivo)

        Files.createDirectories(baseDir)

        if (Files.exists(pathDestino)) {
            throw DocumentoExistenteException(pathDestino.toString())
        }
        documento.transferTo(pathDestino.toFile())

        return pathDestino.toString();
    }

    override fun registrarDocumento(documento: DocumentoCliente) {
        documentoRepository.save(documento)
    }

    override fun buscarDocumentosPorClienteId(clienteId: Long): List<DocumentoCliente> {
        return documentoRepository.findAllByClienteId(clienteId)
    }

    private fun validarExistenciaDocumentos(documento: Map<TipoDocumento, MultipartFile>, cliente: Cliente){
        if(!documento.containsKey(TipoDocumento.RG))
            throw IllegalArgumentException("O RG é um documento obrigatório")

        if(!documento.containsKey(TipoDocumento.CPF))
            throw IllegalArgumentException("O CPF é um documento obrigatório")

//        if(!documento.containsKey(TipoDocumento.CERTIDAO))
//            throw IllegalArgumentException("A Certidão de estado civíl ou nascimento é um documento obrigatório.")
//
//        if(!documento.containsKey(TipoDocumento.HOLERITE))
//            throw IllegalArgumentException("O Holerite é um documento obrigatório.")
//
//        if(!documento.containsKey(TipoDocumento.CARTEIRA_TRABALHO))
//            throw IllegalArgumentException("O Holerite é um documento obrigatório.")
//
//        if(cliente.perfilFinanceiro.desejaUsarFgts == true
//            && !documento.containsKey(TipoDocumento.EXTRATO_FGTS))
//            throw IllegalArgumentException("O Extrato de FGTS é um documento obrigatório.")

    }

}