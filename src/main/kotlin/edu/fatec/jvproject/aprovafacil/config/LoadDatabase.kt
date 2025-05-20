package edu.fatec.jvproject.aprovafacil.config;

import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.enum.TipoImovel
import edu.fatec.jvproject.aprovafacil.enum.TipoRenda
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse
import edu.fatec.jvproject.aprovafacil.model.PerfilFinanceiro
import edu.fatec.jvproject.aprovafacil.repository.IClienteRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate

@Configuration
 class LoadDatabase {

     @Bean
     fun initDatabase(
         clienteRepository: IClienteRepository
     ) : CommandLineRunner {
         return CommandLineRunner {

             val cliente = Cliente(
                 nome = "Moisés",
                 cpf = "24055566688",
                 telefone = "11984756653",
                 email = "moises@gmail.com",
                 dataNascimento = LocalDate.of(1990, 5, 20),
                 statusCadastro = StatusCliente.PENDENTE,
                 perfilFinanceiro = PerfilFinanceiro(
                     rendaBruta = 6500.00,
                     tipoRenda = TipoRenda.FORMAL,
                     temRestricaoCredito = false,
                     temDependente = true,
                     tresAnosFgts = true,
                     desejaUsarFgts = true
                 ),
                 dadosInteresse = DadosInteresse(
                     tipoImovel = TipoImovel.APARTAMENTO,
                     regiaoInteresse = "Zona Sul - São Paulo"
                 )
             )

             clienteRepository.save(cliente)

             println("Banco de dados carregado com sucesso!")
         }
     }

}
