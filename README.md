# Aprova Fácil API (backend)

O Aprova Fácil é uma aplicação desenvolvida para simplificar o envio de dados do cliente à imobiliária para análise de crédito. Seu objetivo é centralizar as informações em uma única plataforma, oferecer uma visualização organizada para o administrador, exibir o status de atendimento dos clientes e facilitar que o corretor registre a devolutiva do atendimento.

Aprova Fácil (frontend) <a href="https://github.com/JV-projects/aprova-facil-kmp" target="_blank">Link do repositório frontend</a>

#### Nosso Cliente

Nosso cliente é a <a href="https://lastrearimoveis.com.br/" target="_blank">Lastrear Imobiliária</a>, uma empresa especializada na venda e locação de imóveis. A equipe nos apresentou a necessidade de uma solução para centralizar e organizar as informações dos clientes, relatando dificuldades no gerenciamento dos dados antes de enviá-los para a análise de crédito e atendimento com corretor.

#### Requisitos Funcionais do Sistema

| Módulo                 | ID   | Nome                                                         | Descrição                                                                                                                                               |
|------------------------|------|--------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Cliente                | RF01 | Preencher formulário                                         | Fornecer um formulário para que o cliente possa preencher seus dados e enviá-los para análise de crédito imobiliário.                                  |
| Cliente                | RF02 | Upload de documentos                                         | O usuário consegue fazer upload dos seus documentos no formulário                                                                                       |
| Cliente                | RF03 | Consultar dados para edição                                  | O cliente consegue consultar seus dados por meio do CPF e email para fazer alterações                                                                   |
| Cliente                | RF04 | Exclusão de dados                                            | O cliente consegue excluir seus dados do sistema                                                                                                        |
| Cliente                | RF05 | Vincular outro participante                                  | Se o cliente desejar, ele consegue vincular um outro participante no seu atendimento informando um CPF. Este participante deve estar previamente cadastrado no sistema. |
| Administrador          | RF06 | Visualizar cliente                                           | O administrador deve conseguir visualizar as informações do cliente.                                                                                     |
| Administrador          | RF07 | Visualizar clientes pendentes                                | Em um dashboard, o sistema deve exibir os clientes pendentes de atendimento, que sua análise de crédito ainda não foi feita                             |
| Administrador          | RF08 | Visualizar clientes aguardando retorno da análise de crédito | Em um dashboard, o sistema deve exibir os cliente que aguardam um retorno da análise de crédito                                                         |
| Administrador          | RF09 | Descrever o retorno da análise de crédito                    | No item de cada cliente listado, deve ser possível inserir o retorno da análise de crédito, para que posteriormente, o corretor consiga acessar essa informação. Note que essa ação 'promove' o cliente para 'pendente de atendimento'. |
| Administrador          | RF10 | Visualizar clientes pendentes de distribuição                | Em um dashboard, o sistema deve exibir os clientes que estão pendentes de serem distribuídos para o atendimento com um corretor.                        |
| Administrador          | RF11 | Distribuir clientes para os corretores                       | Deve ser possível enviar um email pro corretor, informando o cliente destinado a ele bem como o código para realizar a devolutiva.                      |
| Administrador          | RF12 | Visualizar clientes em atendimento                           | Em um dashboard, o sistema deve exibir os clientes que estão em atendimento.                                                                            |
| Administrador          | RF13 | Visualizar clientes com atendimento concluído                | Em um dashboard, o sistema deve exibir os clientes que já foram atendidos, bem como sua devolutiva fornecida pelo corretor.                            |
| Corretor               | RF14 | Realizar devolutiva                                          | O corretor consegue realizar a devolutiva do cliente com o código recebido por email. Ao realizar a devolutiva, o atendimento daquele cliente é concluído. |
| Autenticação | RF15 | Autenticação com username e senha.                           | O administrador consegue se autenticar na plataforma para gerenciar os clientes                                                                         |
### Tecnologias 

- Kotlin Multiplataforma
- Compose Multplatform
- Ktor
- Kotlin
- JWT token
- Spring
  - Spring Boot
  - Spring Web
  - Spring Security
  - Spring JPA
  - JavaMail Sender
  - Validation
  - Thymeleaf
- PostgreSQL

### Interfaces

#### Módulo Cliente

Menu de operações do cliente

![image](https://github.com/user-attachments/assets/6df3b52c-3859-4d17-ac4e-79a9ef016ca6)

Formulário de envio de análise de crédito

![image](https://github.com/user-attachments/assets/fb9d5fe1-088d-4657-abf9-430c7e086321)

Envio de documentos

![image](https://github.com/user-attachments/assets/f38bc469-78c0-4dfc-a72c-fcbc17389d79)

Consultando dados

![image](https://github.com/user-attachments/assets/de365a0e-9409-4809-a1db-42b9f556e8d9)

Detalhes do cliente

![image](https://github.com/user-attachments/assets/d5a01814-5266-4cde-a2b2-cec6cc947725)

#### Módulo Administrador

Tela de acesso interno

![image](https://github.com/user-attachments/assets/28d8d529-72ae-4f84-849f-feadcc3a6e8d)

Login do administrador

![image](https://github.com/user-attachments/assets/9a4c2153-975f-40dd-ad6f-2a45395b1d93)

Dashboard do administrador

![image](https://github.com/user-attachments/assets/3123398e-e315-4405-ac73-fe2244bf874c)

Detalhes e registro de atendimento

![image](https://github.com/user-attachments/assets/7bd2aac9-6965-4627-a583-da5cabbcf5ff)

#### Módulo Corretor

Inserção de código da devolutiva

![image](https://github.com/user-attachments/assets/f93233a5-63dc-4390-b2ee-8e4d05fe4120)

Inserindo devolutiva de atendimento

![image](https://github.com/user-attachments/assets/3e02ce2c-2cdc-43da-ad87-bfc5499680f1)
