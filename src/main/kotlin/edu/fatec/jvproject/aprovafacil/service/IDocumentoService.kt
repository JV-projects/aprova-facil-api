package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.enum.TipoDocumento
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DocumentoCliente
import org.springframework.web.multipart.MultipartFile

interface IDocumentoService {
    /**
     * Processa o Map de documentos enviado
     */
    fun processarMapDocumentos(documentos: Map<TipoDocumento, MultipartFile>, clienteId: Long)
    /**
     * Armazenar o documento de arquivos do servidor
     */
    fun armazenarDocumento(documento: MultipartFile, tipo: TipoDocumento, cliente: Cliente) : String
    /**
     * Registra o documento no banco de dados
     */
    fun registrarDocumento(documento: DocumentoCliente)
    /**
     * Busca todos os documentos de um cliente
     */
    fun buscarDocumentosPorClienteId(clienteId: Long) : List<DocumentoCliente>

    /**
     * Recupeda os documentos do cliente por id
     * */
    fun recuperarDocumentosDoClientePorId(clienteId: Long) : Map<TipoDocumento, String>

}