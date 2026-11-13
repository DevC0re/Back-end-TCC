<div align="center">

# 🏥 MedUp — Back-end

**API REST para uma plataforma de informações e avaliações de unidades de saúde.**

Projeto de Trabalho de Conclusão de Curso (TCC) desenvolvido pela equipe **DevCore**.

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?logo=apachemaven&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-222222?logo=jsonwebtokens)

</div>

## 📖 Sobre o projeto

O **MedUp** tem como objetivo facilitar o acesso a informações sobre hospitais e outras unidades de saúde, reunindo dados e avaliações que ajudem as pessoas a tomar decisões mais informadas sobre atendimento médico.

Este repositório contém o **back-end** da aplicação: uma API REST responsável pela autenticação, persistência e gerenciamento dos dados utilizados pelo aplicativo.

> **Importante:** avaliações e informações publicadas por usuários não devem ser interpretadas como confirmação oficial de disponibilidade ou qualidade dos serviços de saúde.

## ✨ Funcionalidades presentes no código

- **Usuários:** cadastro, consulta, atualização e exclusão de contas.
- **Autenticação:** login por e-mail e senha, com emissão de token JWT.
- **Verificação de e-mail:** envio, confirmação e reenvio de código de verificação.
- **Hospitais:** cadastro, listagem, consulta, exclusão e gerenciamento de endereço.
- **Especialidades médicas:** cadastro e consultas.
- **Avaliações por especialidade:** criação e consulta por hospital.
- **Comentários:** cadastro, listagem, consulta e exclusão.
- **Convênios:** cadastro, listagem, consulta, atualização e exclusão.
- **Endereços do usuário:** cadastro, listagem, consulta, atualização, exclusão e definição de endereço principal.
- **Controle de acesso:** autenticação para endpoints protegidos e restrições administrativas em rotas específicas.
- **Documentação interativa:** Swagger/OpenAPI.

## 🛠️ Tecnologias

| Tecnologia | Utilização |
| --- | --- |
| Java 25 | Linguagem principal, conforme `pom.xml` desta versão |
| Spring Boot 4.1.1 | Base da aplicação |
| Spring Web MVC | Endpoints HTTP REST |
| Spring Data JPA / Hibernate | Persistência e mapeamento de entidades |
| Spring Security | Autenticação e autorização |
| JWT (`jjwt` 0.11.5) | Tokens de acesso |
| BCrypt | Hash de senhas |
| MySQL | Banco de dados relacional |
| Jakarta Validation | Validação das entradas |
| Spring Mail | Envio de e-mails de confirmação |
| Springdoc OpenAPI | Documentação da API |
| Maven Wrapper | Build e gerenciamento de dependências |
| Lombok | Redução de código repetitivo |

## 📁 Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/devcore/medup/
│   │   ├── config/          # Segurança JWT, CORS e Swagger
│   │   ├── controllers/     # Endpoints REST
│   │   ├── dtos/
│   │   │   ├── request/     # Objetos de entrada
│   │   │   └── reponse/     # Objetos de resposta (nome atual do pacote)
│   │   ├── entities/       # Entidades persistidas no banco
│   │   ├── enums/          # Enumerações do domínio
│   │   ├── repositories/   # Repositórios JPA
│   │   ├── services/       # Regras de negócio
│   │   └── MedupApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/devcore/medup/
```

## ⚙️ Pré-requisitos

- **JDK 25** (a configuração `java.version` no `pom.xml` está definida como `25`).
- **MySQL** acessível, localmente ou na nuvem.
- **Git** para clonar o repositório.
- **Credenciais SMTP** para o envio de códigos de verificação por e-mail.

O repositório já possui o **Maven Wrapper**, portanto não é obrigatório instalar o Maven separadamente.

## 🚀 Como executar

### 1. Clonar o repositório

```bash
git clone https://github.com/DevC0re/Back-end-TCC.git
cd Back-end-TCC
```

### 2. Criar ou selecionar o banco de dados

Se utilizar MySQL local, crie um banco:

```sql
CREATE DATABASE medup CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

O projeto utiliza `spring.jpa.hibernate.ddl-auto=update`, que permite ao Hibernate atualizar as tabelas conforme o mapeamento das entidades. **Isso não popula automaticamente o banco com hospitais.**

### 3. Configurar as variáveis de ambiente

O arquivo `src/main/resources/application.properties` lê as seguintes variáveis:

| Variável | Descrição | Exemplo |
| --- | --- | --- |
| `DB_URL` | URL JDBC do MySQL | `jdbc:mysql://localhost:3306/medup` |
| `DB_USERNAME` | Usuário do banco | `root` |
| `DB_PASSWORD` | Senha do banco | `sua_senha` |
| `JWT_SECRET` | Segredo de assinatura do JWT | `chave_forte_e_privada` |
| `MAIL_HOST` | Servidor SMTP | `smtp.gmail.com` |
| `MAIL_PORT` | Porta SMTP | `465` |
| `MAIL_USERNAME` | E-mail remetente | `seu-email@gmail.com` |
| `MAIL_PASSWORD` | Senha de aplicativo/credencial SMTP | `sua_credencial` |

Exemplo no **PowerShell (Windows)**, antes de iniciar a aplicação:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/medup"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "sua_senha"
$env:JWT_SECRET = "substitua-por-uma-chave-secreta-forte"
$env:MAIL_USERNAME = "seu-email@gmail.com"
$env:MAIL_PASSWORD = "sua-senha-de-aplicativo"
```

> Nunca faça commit de credenciais reais. O projeto também contém um endereço de banco remoto como valor padrão para `DB_URL`; defina explicitamente `DB_URL` para o ambiente correto. Se o banco for remoto, verifique as exigências de TLS/SSL do provedor.

### 4. Iniciar o back-end

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

Por padrão, na ausência de outra configuração de porta, a API é acessada em `http://localhost:8080`.

### 5. Acessar o Swagger

Abra:

**http://localhost:8080/swagger-ui/index.html**

Especificação OpenAPI: `http://localhost:8080/v3/api-docs`.

## 🔐 Autenticação

O cadastro e o login são públicos, assim como as rotas de confirmação e reenvio de código. As demais rotas exigem autenticação, salvo as exceções configuradas no `SecurityConfig`.

### Cadastro

`POST /usuario`

```json
{
  "name": "Maria Silva",
  "email": "maria@example.com",
  "cpf": "12345678909",
  "password": "senha-forte"
}
```

*Os dados acima são apenas ilustrativos.* O cadastro utiliza verificação por e-mail, então o SMTP precisa estar configurado.

### Confirmar e-mail

`POST /api/auth/confirmar`

```json
{
  "email": "maria@example.com",
  "codigo": "123456"
}
```

Para solicitar novo código: `POST /api/auth/reenviar`, enviando `{"email":"maria@example.com"}`.

### Login

`POST /login`

```json
{
  "email": "maria@example.com",
  "senha": "senha-forte"
}
```

**Atenção:** na implementação atual, a resposta de login é a **string do token JWT**, e não um objeto JSON com a propriedade `token`.

Para rotas protegidas, envie o cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

No Swagger, clique em **Authorize** para informar o token.

## 📡 Principais endpoints

As rotas abaixo foram identificadas diretamente nos controllers desta versão. `{id}` representa um identificador UUID.

| Método | Endpoint | Ação |
| --- | --- | --- |
| `POST` | `/usuario` | Criar usuário |
| `GET` | `/usuario` | Listar usuários (ADMIN) |
| `GET` | `/usuario/{id}` | Consultar usuário |
| `PUT` | `/usuario/{id}` | Atualizar usuário |
| `DELETE` | `/usuario/{id}` | Excluir usuário |
| `GET` | `/usuario/admin` | Verificação de acesso administrativo |
| `POST` | `/login` | Autenticar e obter JWT |
| `POST` | `/api/auth/confirmar` | Confirmar e-mail |
| `POST` | `/api/auth/reenviar` | Reenviar código |
| `POST` | `/hospitais` | Cadastrar hospital |
| `GET` | `/hospitais` | Listar hospitais |
| `GET` | `/hospitais/{id}` | Consultar hospital |
| `DELETE` | `/hospitais/{id}` | Excluir hospital |
| `GET` | `/hospitais/{id}/endereco` | Consultar endereço do hospital |
| `PUT` | `/hospitais/{id}/endereco` | Alterar endereço do hospital (ADMIN) |
| `POST` | `/especialidades` | Criar especialidade |
| `GET` | `/especialidades` | Listar especialidades |
| `GET` | `/especialidades/{id}` | Consultar especialidade |
| `POST` | `/avaliacoes-especialidades` | Criar avaliação por especialidade |
| `GET` | `/avaliacoes-especialidades/hospital/{hospitalId}` | Listar avaliações por hospital |
| `POST` | `/comments` | Criar comentário |
| `GET` | `/comments` | Listar comentários |
| `GET` | `/comments/{id}` | Consultar comentário |
| `DELETE` | `/comments/{id}` | Excluir comentário |
| `POST` | `/convenio` | Criar convênio |
| `GET` | `/convenio` | Listar convênios |
| `GET` | `/convenio/{id}` | Consultar convênio |
| `PUT` | `/convenio/{id}` | Atualizar convênio |
| `DELETE` | `/convenio/{id}` | Excluir convênio |
| `GET` | `/usuario/me/enderecos` | Listar meus endereços |
| `GET` | `/usuario/me/enderecos/{id}` | Consultar meu endereço |
| `POST` | `/usuario/me/enderecos` | Adicionar endereço |
| `PUT` | `/usuario/me/enderecos/{id}` | Atualizar endereço |
| `PATCH` | `/usuario/me/enderecos/{id}/principal` | Definir endereço principal |
| `DELETE` | `/usuario/me/enderecos/{id}` | Excluir endereço |

### Exemplo: cadastrar um endereço

`POST /usuario/me/enderecos`

```json
{
  "cep": "01310100",
  "logradouro": "Avenida Paulista",
  "numero": "1000",
  "bairro": "Bela Vista",
  "cidade": "São Paulo",
  "estado": "SP",
  "complemento": "",
  "apelido": "Casa",
  "principal": true
}
```

Os campos de latitude e longitude também estão disponíveis no DTO de endereço.

## 🗄️ Modelagem e camadas

A estrutura segue separação por responsabilidades:

1. **Controller:** recebe requisições HTTP e devolve respostas.
2. **DTO:** define os dados de entrada e saída.
3. **Service:** concentra o processamento e as regras de negócio.
4. **Repository:** realiza consultas e operações no banco por meio do Spring Data JPA.
5. **Entity:** representa os registros e relacionamentos persistidos.

Entre as entidades encontradas estão `UserEntity`, `HospitaisEntity`, `EnderecoEntity`, `ConvenioEntity`, `EspecialidadeEntity`, `AvaliacaoEspecialidadeEntity`, `Comments` e `TokenVerificacao`.

## 🧪 Testes e build

Para executar os testes existentes:

**Windows:**

```powershell
.\mvnw.cmd test
```

**Linux/macOS:**

```bash
./mvnw test
```

Para gerar o artefato:

```bash
./mvnw clean package
```

O JAR é gerado na pasta `target/` em caso de build bem-sucedido. **A presença de uma classe de teste no repositório não garante cobertura completa das funcionalidades.**

## ⚠️ Observações sobre a versão atual

- O projeto exige **Java 25** conforme o `pom.xml` enviado; para usar Java 17, é necessário revisar a configuração e a compatibilidade antes do build.
- Na configuração atual do Spring Security, as rotas não explicitamente liberadas exigem autenticação. Isso inclui, por exemplo, o `GET /hospitais`.
- A configuração de CORS permite origens `http://localhost` com qualquer porta; outros domínios e origens devem ser configurados conforme o ambiente.
- A exclusão de comentários em `CommentsService` **não verifica se a pessoa é autora ou administradora** nesta versão. Essa autorização precisa ser implementada antes de tratar a funcionalidade como concluída.
- A criação e a exclusão de hospitais não possuem, nos respectivos métodos do controller, uma checagem explícita de papel ADMIN; revise as permissões conforme as regras de negócio planejadas.
- A configuração `ddl-auto=update` é conveniente no desenvolvimento, mas ambientes de produção devem adotar uma estratégia controlada de migração do banco.
- As instruções documentam o código encontrado; o build e todos os fluxos de execução não foram validados neste ambiente.

## 👥 Equipe e repositório

**DevCore — Projeto MedUp (TCC)**

Repositório: [DevC0re/Back-end-TCC](https://github.com/DevC0re/Back-end-TCC)

---

<div align="center">

**MedUp — Informação para decisões mais conscientes sobre saúde.**

</div>
