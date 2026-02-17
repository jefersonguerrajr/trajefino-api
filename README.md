# Trajefino API

Serviço desenvolvido utilizando o framework **Spring Boot** para o sistema trajefino.

> Porta padrão da aplicação: **1515** (configurada em `src/main/resources/application.yml`).

## Requisitos

- **Java 21**
- **Gradle** (ou use o wrapper `gradlew`/`gradlew.bat` que já vem no projeto)
- **PostgreSQL**

## Configuração

A aplicação usa variáveis de ambiente para conexão com o banco (ver `application.yml`).

### Variáveis suportadas

| Variável | Padrão | Descrição |
|---|---:|---|
| `DB_HOST` | `localhost` | Host do PostgreSQL |
| `DB_PORT` | `5432` | Porta do PostgreSQL |
| `DB_NAME` | `postgresdb` | Nome do banco |
| `DB_USER` | `pgadmin` | Usuário do banco |
| `DB_PASSWORD` | `12345` | Senha do banco |


### Exemplo (PowerShell)

```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
$env:DB_NAME="postgresdb"
$env:DB_USER="pgadmin"
$env:DB_PASSWORD="12345"
```

## Execução

### Subir a aplicação (PowerShell)

```powershell
./gradlew.bat bootRun
```

A API sobe em:

- http://localhost:1515

### Build

```powershell
./gradlew.bat build
```

### Testes

```powershell
./gradlew.bat test
```

## Documentação Swagger

A UI do Swagger fica disponível em:

- http://localhost:1515/swagger-ui.html

E a especificação OpenAPI em:

- http://localhost:1515/v3/api-docs

## Autenticação (JWT)

Os endpoints de autenticação ficam em `/auth/**`. Em geral o fluxo é:

1. **Registrar** um usuário (`POST /auth/register`) ou criar usuários via endpoints de usuário.
2. **Login** (`POST /auth/login`) para receber o token JWT.
3. Enviar o token nas requisições usando o header:

```
Authorization: Bearer <SEU_TOKEN>
```

## Tecnologias

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (`io.jsonwebtoken:jjwt`)
- Swagger / OpenAPI (`springdoc-openapi-starter-webmvc-ui`)
- Lombok
