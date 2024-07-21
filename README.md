# Rest API - Parking Management 
Bem-vindo à Parking API! Este projeto é uma API RESTful desenvolvida com **Spring Boot 3** e **Java 17**.

Esta API RESTful permite gerenciar o estacionamento de veículos, incluindo operações de CRUD e geração de relatórios. A segurança é garantida com autenticação JWT e Spring Security. A documentação da API é feita com OpenAPI e Swagger.

## Funcionalidades

#### Autenticação e Autorização:
- [x] Segurança com Spring Security
- [x] Autenticação utilizando Tokens JWT
#### Operações de CRUD:
- [x] Gerenciamento de Usuários
      
      - [POST] /api/v1/usuarios - Cadastra um usuário
            {
                "username": "admin@email.com",
                "password": "123456"
            }
      - [POST] /api/v1/auth - Autentica o usuário cadastrado e gera um token
      - [GET] /api/v1/usuarios - Retorna todos os usuários
      - [GET] /api/v1/usuarios/{id} - Retorna usuário por ID
      - [PATCH] /api/v1/usuarios/{id} - Atualiza a senha de um usuário
      
- [ ] Gerenciamento de Clientes
- [ ] Gerenciamento de Veículos
- [ ] Gerenciamento de Estacionamentos
#### Documentação:
- [x] Documentação da API com OpenAPI e Swagger (Pode ser acessada em http://localhost:8080/docs-parking-api.html após iniciar a aplicação).
#### Validações e Auditoria:
- [x] Validação de campos com Jakarta Bean Validation
- [x] Configuração de auditoria com Spring-JPA-Auditing
#### Relatórios:
- [ ] Geração de relatórios com JasperReports e JasperSoft Studio
#### Testes:
- [ ] Camada de testes de ponto a ponto (End2End)

### Tecnologias Utilizadas

**Backend:**
- Java 17
- Spring Boot 3

**Segurança:**
- Spring Security
- JWT

**Banco de Dados:**
- Spring Data JPA
- H2 Database (no profile `application-dev` e no `application-test`)
- MySQL (no profile `application`)

**Validação:**
- Jakarta Bean Validation

**Documentação:**
- OpenAPI e Swagger

**Mapeamento de Objetos:**
- Data Transfer Object - DTO
- ModelMapper

**Auditoria:**
- Spring-JPA-Auditing

**Relatórios:**
- JasperReports e JasperSoft Studio

## Instalação

#### Pré-requisitos

- Java 17
- Maven
- H2 Database

#### Passos
1. **Clone o repositório**
   ```bash
   git clone https://github.com/otthonleao/spring-parking-api.git
   cd spring-parking-api
   ```
2. **Configure o banco de dados**

   O perfil ativo nesse repositório é o `application-dev` e nele está apontado para o banco em memória H2 Database e quando iniciar a aplicação uma carga inicial de dados será carregada por meio do script do `import.sql`.

   Caso queira utilizar algum SGDB basta alterar para o perfil principal ou substituir os dados do datasource:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/parking-api?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Manaus
   spring.datasource.username=seu-usuario
   spring.datasource.password=sua-senha
   ```
3. **Compile e execute a aplicação**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```


