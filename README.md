## Descrição
Esta PR introduz a funcionalidade de gerenciamento de **Hospitais**, incluindo suas **Especialidades**, **Avaliações** e **Comentários** no sistema MedUp.

---

## Alterações Realizadas
- **Controllers:**
  - `HospitaisController`
  - `EspecialidadeController`
  - `AvaliacaoEspecialidadeController`
  - `CommentsController`
- **Entities & DTOs:**
  - Adicionados os DTOs de Request/Response para as entidades manipuladas.
  - Mapeamento das tabelas/entidades JPA correspondentes.
- **Services & Repositories:**
  - Regras de negócio e persistência de dados para hospitais e especialidades.

---

## Como Testar
1. Suba a aplicação Spring Boot (`./mvnw spring-boot:run` ou pela IDE).
2. Certifique-se de que o banco de dados está rodando e configurado em `application.properties`.
3. Teste os endpoints REST criados para a rota de Hospitais e Especialidades através do Postman, Insomnia ou Swagger.

---

## Checklist
- [x] O código segue os padrões do projeto.
- [x] As dependências e estruturas de pacotes estão corretas.
- [ ] Testes unitários/integração adicionados/atualizados (se aplicável).


## Confirmação de e-mail por código de 6 dígitos

O cadastro (`POST /usuario`) cria o usuário com `active=false`, gera um código numérico aleatório e envia esse código por e-mail. O código expira em 10 minutos. O banco armazena somente o hash SHA-256 do código.

Para confirmar a conta, envie:

```http
POST /api/auth/confirmar
Content-Type: application/json
```

```json
{
  "email": "usuario@exemplo.com",
  "codigo": "483921"
}
```

A conta é ativada somente se o código corresponder e ainda estiver válido. Depois do sucesso, o token é removido e não pode ser reutilizado. O frontend deve mostrar um campo para o e-mail e outro para o código recebido.
