package edu.fatec.jvproject.aprovafacil.service

import edu.fatec.jvproject.aprovafacil.dto.ConteudoEmail
import edu.fatec.jvproject.aprovafacil.dto.ConteudoTemplate
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailSendException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailSenderService(
    private val emailSender: JavaMailSender,
    private val templateEngine: TemplateEngine,
    @Value("\${mail.service.assunto}")
    private val assunto: String,
) {
    fun enviarEmail(conteudoTemplate: ConteudoTemplate, destinatario: String) {
        val htmlContent = criarEmailComTemplate(conteudoTemplate)
        val conteudo = ConteudoEmail(
            to = destinatario,
            subject = assunto,
            text = htmlContent
        )

        try {
            val msg = criarMensagemEmail(conteudo)
            emailSender.send(msg)
        } catch (e: Exception) {
            println("Erro ao enviar email ${e.message}")
            throw MailSendException("Failed", e)
        }
    }

    private fun criarMensagemEmail(emailMessage: ConteudoEmail): MimeMessage {
        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message)

        helper.setTo(emailMessage.to)
        helper.setSubject(emailMessage.subject)
        helper.setText(emailMessage.text, true)

        return message
    }

    fun criarEmailComTemplate(conteudoTemplate: ConteudoTemplate): String {
        val context = Context().apply {
            setVariable("conteudo", conteudoTemplate)
        }
        return templateEngine.process("email-template", context)
    }
}