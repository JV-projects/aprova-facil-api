package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.enum.TipoDocumento
import edu.fatec.jvproject.aprovafacil.exceptions.DocumentoException
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DocumentoCliente
import edu.fatec.jvproject.aprovafacil.repository.IClienteRepository
import edu.fatec.jvproject.aprovafacil.repository.IDocumentoClienteRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Service
class DocumentoService(
    val clienteRepository: IClienteRepository,
    val documentoRepository: IDocumentoClienteRepository
) : IDocumentoService {

    override fun processarDocumentosBase64(documentos: Map<TipoDocumento, String>, cliente: Cliente) : List<DocumentoCliente> {

        validarExistenciaDocumentos(documentos)
        var documentosSalvos = mutableListOf<DocumentoCliente>()
        documentos.forEach { (tipo, base64String) ->
            val bytes = Base64.getDecoder().decode(base64String)
            if (!isPdf(bytes)) {
                throw DocumentoException("Tipo de documento inválido para $tipo. Apenas PDFs são permitidos.")
            }
            val tempMultipartFile: MultipartFile = createMultipartFileFromBytes(bytes, "documento", tipo.name.lowercase() + ".pdf") // Add appropriate filename/extension

            val arquivoArmazenado = armazenarDocumento(tempMultipartFile, tipo, cliente)

            documentosSalvos.add(registrarDocumento(
                DocumentoCliente(
                    arquivoArmazenado,
                    tipo,
                    cliente
                )
            ))
        }

        return documentosSalvos
    }

    override fun processarMapDocumentos(documentos: Map<TipoDocumento, MultipartFile>, clienteId: Long) {
        val cliente = clienteRepository.findById(clienteId)
            .orElseThrow { RuntimeException("Cliente não encontrado") }

        documentos.forEach { (tipo, arquivo) ->
            val arquivoArmazenado = armazenarDocumento(arquivo, tipo, cliente)
            val nomeArquivo = Paths.get(arquivoArmazenado).fileName.toString()
            registrarDocumento(
                DocumentoCliente(
                    nomeArquivo,
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
           Files.delete(pathDestino)
        }
        documento.transferTo(pathDestino.toFile())

        return pathDestino.toString();
    }

    override fun registrarDocumento(documento: DocumentoCliente) : DocumentoCliente{
        return documentoRepository.save(documento)
    }

    override fun buscarDocumentosPorClienteId(clienteId: Long): List<DocumentoCliente> {
        return documentoRepository.findAllByClienteId(clienteId)
    }

    override fun recuperarDocumentosDoClientePorId(clienteId: Long): Map<TipoDocumento, String> {
        val pastaDocumentos = Paths.get("documentos")
        if (!Files.exists(pastaDocumentos)) return emptyMap()

        val prefixo = "${clienteId}_"

        return Files.list(pastaDocumentos)
            .toList()
            .filter { it.fileName.toString().startsWith(prefixo) }
            .mapNotNull { path ->
                val nomeArquivo = path.fileName.toString()
                val tipo = extrairTipoDocumento(nomeArquivo) ?: return@mapNotNull null
                val bytes = Files.readAllBytes(path)
                val base64 = Base64.getEncoder().encodeToString(bytes)

                tipo to base64
            }
            .toMap()
    }

    fun extrairTipoDocumento(nomeArquivo: String): TipoDocumento? {
        val semExtensao = nomeArquivo.substringBeforeLast('.')
        val tipoStr = semExtensao.substringAfter("_").uppercase()
        return try {
            TipoDocumento.valueOf(tipoStr)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun validarExistenciaDocumentos(documento: Map<TipoDocumento, String>) {
        if (!documento.containsKey(TipoDocumento.RG))
            throw DocumentoException("O RG é um documento obrigatório")

        if (!documento.containsKey(TipoDocumento.CPF))
            throw DocumentoException("O CPF é um documento obrigatório")

        if (!documento.containsKey(TipoDocumento.CERTIDAO))
            throw DocumentoException("A Certidão de estado civíl ou de nascimento é um documento obrigatório.")

        if (!documento.containsKey(TipoDocumento.COMPROVANTE_RESIDENCIA))
            throw DocumentoException("O comprovante de residência é um documento obrigatório.")

        if (!documento.containsKey(TipoDocumento.PIS_CARTAO_CIDADAO))
            throw DocumentoException("O PIS ou Cartão do Cidadão é um documento obrigatório.")

        if (!documento.containsKey(TipoDocumento.HOLERITE))
            throw DocumentoException("O Holer// Retorna o tamanho do array de bytesite é um documento obrigatório.")

        if (!documento.containsKey(TipoDocumento.CARTEIRA_TRABALHO))
            throw DocumentoException("O Holerite é um documento obrigatório.")

        if (!documento.containsKey(TipoDocumento.EXTRATO_FGTS))
            throw DocumentoException("O Extrato de FGTS é um documento obrigatório.")

        if (!documento.containsKey(TipoDocumento.DECLARACAO_IR))
            throw DocumentoException("A declaração de imposto de renda é um documento obrigatório.")
    }

    fun createMultipartFileFromBytes(bytes: ByteArray, name: String, originalFilename: String): MultipartFile {
        return object : MultipartFile {

            override fun getName(): String = name
            override fun getOriginalFilename(): String = originalFilename

            override fun getContentType(): String? = null

            override fun isEmpty(): Boolean = bytes.isEmpty()
            override fun getSize(): Long = bytes.size.toLong()
            override fun getBytes(): ByteArray = bytes
            override fun getInputStream(): InputStream =
                ByteArrayInputStream(bytes)

            override fun transferTo(dest: File) {
                FileOutputStream(dest).use { it.write(bytes) }
            }
        }
    }

    fun isPdf(bytes: ByteArray): Boolean {
        return bytes.startsWith(byteArrayOf(0x25, 0x50, 0x44, 0x46))
    }

    fun ByteArray.startsWith(prefix: ByteArray): Boolean {
        if (this.size < prefix.size) return false
        return this.sliceArray(0 until prefix.size).contentEquals(prefix)
    }

}