# MedUp API — Validação de E-mail por Código

## Visão geral

Este módulo implementa a confirmação de endereço de e-mail no MedUp por meio de um código numérico de seis dígitos. O código é gerado pela API, enviado ao endereço informado no cadastro e validado por um endpoint específico.

O usuário permanece inativo até que a confirmação seja concluída com sucesso.

## Objetivos

- Confirmar que o usuário tem acesso ao endereço de e-mail informado.
- Impedir o acesso de usuários que ainda não confirmaram o e-mail.
- Gerar códigos de verificação temporários.
- Validar o código informado pelo usuário por meio da API.
- Ativar a conta após a validação bem-sucedida.
- Integrar o envio de e-mail ao backend Spring Boot.

## Fluxo de funcionamento

1. O cliente solicita o cadastro do usuário.
2. A API verifica se já existe uma conta com o e-mail informado.
3. A API cria o usuário com a conta inativa (`active = false`).
4. A API gera um código de verificação de seis dígitos e associa a verificação ao usuário e à finalidade correspondente.
5. A API envia o código ao endereço de e-mail cadastrado.
6. O usuário informa o e-mail e o código recebido.
7. A API verifica se a verificação existe, se corresponde ao usuário e à finalidade correta, se ainda está válida e se o código informado confere.
8. Se a validação for bem-sucedida, a verificação é invalidada e a conta é ativada.
9. Enquanto a conta estiver inativa, o usuário não deve conseguir autenticar-se.

## Componentes envolvidos

Os nomes exatos podem variar conforme a versão do código integrada ao repositório.

| Componente | Responsabilidade |
|---|---|
| `UserService` | Executar o cadastro e iniciar o processo de verificação. |
| `UserEntity` | Representar o usuário e armazenar o estado da conta, incluindo `active`. |
| `VerificationPurpose` | Identificar a finalidade da verificação por meio de um `enum`. |
| `TokenVerificacao` ou entidade equivalente | Persistir os dados da verificação, como usuário, finalidade, prazo de validade e representação segura do código. |
| Repositório de verificação | Consultar, persistir e invalidar registros de verificação. |
| `EmailService` | Enviar o código por e-mail usando a configuração SMTP. |
| `EmailVerificationService` ou serviço equivalente | Gerar e validar códigos e ativar a conta após a confirmação. |
| `EmailVerificationController` ou controlador equivalente | Expor o endpoint que recebe os dados de confirmação. |
| DTO de confirmação | Validar os dados recebidos pela API, como e-mail e código. |
| Configuração do Spring Security | Restringir a autenticação de contas inativas e permitir o acesso ao endpoint de confirmação sem autenticação prévia, conforme a configuração do projeto. |

## Endpoint de confirmação

Consulte o controlador correspondente para confirmar o caminho real do endpoint. No exemplo abaixo, considera-se:

`POST /api/auth/confirmar`

Exemplo de corpo da requisição:

```json
{
  "email": "usuario@exemplo.com",
  "codigo": "123456"
}
```

O valor `123456` é apenas ilustrativo. Deve ser enviado o código recebido por e-mail.

### Respostas esperadas

As respostas HTTP exatas dependem da implementação do controlador. Em geral:

- `2xx`: código validado e conta ativada.
- `400 Bad Request`: dados ausentes ou em formato inválido.
- `404 Not Found` ou resposta equivalente: verificação não encontrada.
- `409 Conflict` ou resposta equivalente: conta já confirmada ou estado incompatível.
- `422 Unprocessable Entity` ou resposta equivalente: código incorreto ou expirado.

O endpoint de cadastro e o formato da resposta de sucesso devem ser conferidos no controlador de usuários do projeto.

## Configuração do envio de e-mail

Exemplo de configuração para Gmail usando SSL na porta 465:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=465
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.starttls.enable=false
spring.mail.properties.mail.smtp.connectiontimeout=15000
spring.mail.properties.mail.smtp.timeout=15000
spring.mail.properties.mail.smtp.writetimeout=15000
```

Defina as variáveis de ambiente `MAIL_USERNAME` e `MAIL_PASSWORD` antes de iniciar a aplicação.

- `MAIL_USERNAME`: endereço da conta que enviará as mensagens.
- `MAIL_PASSWORD`: senha de aplicativo ou credencial SMTP apropriada para a conta.

Se for utilizado outro provedor, ajuste host, porta e parâmetros TLS/SSL conforme a documentação do provedor.

### Segurança das credenciais

Não grave credenciais reais no repositório. Não envie senhas de aplicativo, tokens ou segredos para o Git. Mantenha as credenciais em variáveis de ambiente ou em um gerenciador de segredos.

## Validade e armazenamento do código

Na implementação descrita, o código tem um prazo de validade configurado de dez minutos. Confirme o valor no serviço responsável pela geração da verificação antes de alterá-lo ou documentá-lo como requisito definitivo.

Quando o código é persistido como hash, a API deve comparar o valor informado com o hash armazenado, sem registrar o código em logs. O código deve ser invalidado após a confirmação bem-sucedida para impedir reutilização.

## Segurança e recomendações

- Manter a conta inativa até a confirmação bem-sucedida.
- Usar códigos aleatórios e temporários.
- Validar a finalidade da verificação, o usuário associado e o prazo de validade.
- Invalidar o código após o uso.
- Limitar as tentativas de validação e o número de solicitações de novos códigos.
- Evitar informar publicamente se um endereço de e-mail está cadastrado, especialmente em endpoints de reenvio.
- Não registrar códigos completos, tokens ou credenciais nos logs.
- Aplicar HTTPS em ambientes de produção.
- Garantir que o Spring Security bloqueie a autenticação de usuários inativos.
- Considerar transações para manter consistentes o cadastro, a criação da verificação e a ativação da conta.
- Definir política de reenvio e invalidar códigos antigos quando um novo for emitido.

## Como testar

1. Configure as variáveis de ambiente de e-mail.
2. Inicie a aplicação e confirme que ela se conecta ao banco de dados.
3. Cadastre um usuário com um endereço de e-mail acessível.
4. Verifique se o usuário foi criado como inativo.
5. Confirme se o e-mail com o código foi recebido.
6. Envie o e-mail e o código para o endpoint de confirmação usando Swagger, Postman ou o cliente frontend.
7. Verifique se a API retorna sucesso e se o usuário passa a estar ativo.
8. Tente validar com um código incorreto e com um código expirado.
9. Confirme que um código já utilizado não pode ser reutilizado.
10. Tente autenticar antes da confirmação e confirme que o acesso é bloqueado.

Exemplo de consulta SQL para verificar o estado da conta (ajuste o nome da tabela e das colunas à entidade real):

```sql
SELECT id, email, active
FROM user_entity
WHERE email = 'usuario@exemplo.com';
```

Não compartilhe códigos de confirmação, tokens ou credenciais ao solicitar suporte.

## Itens a confirmar antes da produção

- Confirmar os nomes reais dos endpoints, DTOs, tabelas e classes no código integrado.
- Confirmar se o limite de tentativas está implementado.
- Confirmar se existe endpoint de reenvio seguro do código.
- Confirmar o tratamento de concorrência e a transação entre cadastro, envio e ativação.
- Testar a integração SMTP e o comportamento do envio assíncrono.
- Adicionar testes automatizados para código válido, incorreto, expirado, reutilizado e conta já ativa.

## Tecnologias

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Jakarta Mail / `JavaMailSender`
- Banco de dados configurado pelo projeto
- SMTP para envio de e-mail

## Observação

Este documento descreve o fluxo de validação por código de seis dígitos integrado ao MedUp. Os nomes de classes, os caminhos dos endpoints e as respostas HTTP devem ser alinhados ao estado final do código no repositório.
