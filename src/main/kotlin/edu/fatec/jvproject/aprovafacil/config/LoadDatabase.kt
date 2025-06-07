package edu.fatec.jvproject.aprovafacil.config;

import edu.fatec.jvproject.aprovafacil.enum.EstadoCivil
import edu.fatec.jvproject.aprovafacil.enum.EstadoImovel
import edu.fatec.jvproject.aprovafacil.enum.StatusCliente
import edu.fatec.jvproject.aprovafacil.enum.TipoImovel
import edu.fatec.jvproject.aprovafacil.enum.TipoRenda
import edu.fatec.jvproject.aprovafacil.model.Atendimento
import edu.fatec.jvproject.aprovafacil.model.Cliente
import edu.fatec.jvproject.aprovafacil.model.DadosInteresse
import edu.fatec.jvproject.aprovafacil.model.PerfilFinanceiro
import edu.fatec.jvproject.aprovafacil.repository.IAtendimentoRepository
import edu.fatec.jvproject.aprovafacil.repository.IClienteRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate

@Configuration
 class LoadDatabase {

     @Bean
     fun initDatabase(
         clienteRepository: IClienteRepository,
         atendimentoRepository: IAtendimentoRepository
     ) : CommandLineRunner {
         return CommandLineRunner {

             var cliente = Cliente(
                 nome = "Moisés",
                 cpf = "24055566688",
                 telefone = "11984756653",
                 email = "moises@gmail.com",
                 dataNascimento = LocalDate.of(1990, 5, 20),
                 statusCadastro = StatusCliente.PENDENTE,
                 estadoCivil = EstadoCivil.SOLTEIRO,
                 perfilFinanceiro = PerfilFinanceiro(
                     rendaBruta = 6500.00,
                     tipoRenda = TipoRenda.FORMAL,
                     possuiRestricao = false,
                     possuiDependente = true,
                     tresAnosFgts = true,
                     usarFgts = true
                 ),
                 dadosInteresse = DadosInteresse(
                     tipoImovel = TipoImovel.APARTAMENTO,
                     estadoImovel = EstadoImovel.USADO
                 )
             )

             val cliente2 = Cliente(
                 nome = "Maria",
                 cpf = "23456789000",
                 telefone = "11984756653",
                 email = "maria@gmail.com",
                 dataNascimento = LocalDate.of(1990, 5, 20),
                 statusCadastro = StatusCliente.PENDENTE,
                 estadoCivil = EstadoCivil.CASADO,
                 perfilFinanceiro = PerfilFinanceiro(
                     rendaBruta = 6500.00,
                     tipoRenda = TipoRenda.AUTONOMO,
                     possuiRestricao = false,
                     possuiDependente = true,
                     tresAnosFgts = true,
                     usarFgts = true
                 ),
                 dadosInteresse = DadosInteresse(
                     tipoImovel = TipoImovel.APARTAMENTO,
                     estadoImovel = EstadoImovel.NOVO
                 )
             )


             cliente = clienteRepository.save(cliente)
             clienteRepository.save(cliente2)

             val atendimento = Atendimento(
                cliente = cliente,
                 analiseCredito = "Crédito de R$20.000,00",
                 emailCorretor = "email@gmail.com",
             )

             atendimentoRepository.save(atendimento)

             println("Banco de dados carregado com sucesso!")
         }
     }

}
