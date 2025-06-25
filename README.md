# Backend Spring Boot - Sistema de Pulverização

API REST desenvolvida com Spring Boot para gerenciamento de aplicações de pulverização agrícola.

## 🏗️ Tecnologias

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **AWS Cognito** (Autenticação)
- **Swagger/OpenAPI** (Documentação)
- **Maven** (Build)
- **Docker**

## 📂 Estrutura do Projeto

```
spring-api/
├── src/main/java/br/edu/utfpr/springapi/
│   ├── controller/          # Controllers REST
│   │   ├── AplicacaoController.java
│   │   ├── AuthController.java
│   │   ├── EquipamentoController.java
│   │   ├── TalhaoController.java
│   │   ├── TipoAplicacaoController.java
│   │   └── UsuarioController.java
│   ├── dto/                # Data Transfer Objects
│   │   ├── AplicacaoDTO.java
│   │   ├── AuthRequest.java
│   │   ├── AuthResponseDTO.java
│   │   └── ...
│   ├── model/              # Entidades JPA
│   │   ├── Aplicacao.java
│   │   ├── BaseEntity.java
│   │   ├── Equipamento.java
│   │   ├── Talhao.java
│   │   ├── TipoAplicacao.java
│   │   └── Usuario.java
│   ├── repository/         # Repositórios Spring Data
│   │   ├── AplicacaoRepository.java
│   │   ├── EquipamentoRepository.java
│   │   └── ...
│   ├── security/           # Configuração de Segurança
│   │   ├── SecurityConfig.java
│   │   ├── JwtAuthenticationFilter.java
│   │   ├── JwtTokenProvider.java
│   │   └── CognitoJwtValidator.java
│   └── service/            # Lógica de Negócio
└── src/main/resources/
    └── application.properties
```

## 🚀 Executando Localmente

### Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL (ou Docker)

### 1. Configuração do Banco de Dados

```bash
# Com Docker
docker run --name postgres-spray \
  -e POSTGRES_DB=tacdb \
  -e POSTGRES_USER=tacdb_owner \
  -e POSTGRES_PASSWORD=npg_lUXQNZzx2J1d \
  -p 5432:5432 -d postgres:15-alpine
```

### 2. Configuração das Variáveis de Ambiente

Edite `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/tacdb
spring.datasource.username=tacdb_owner
spring.datasource.password=npg_lUXQNZzx2J1d

# AWS Cognito
aws.cognito.region=sa-east-1
aws.cognito.url=https://cognito-idp.sa-east-1.amazonaws.com
aws.cognito.userPoolId=your_pool_id
aws.cognito.clientId=your_client_id
aws.cognito.clientSecret=your_client_secret
```

### 3. Executar a Aplicação

```bash
# Compilar e executar
mvn spring-boot:run

# Ou compilar JAR e executar
mvn clean package -DskipTests
java -jar target/api1-*.jar
```

### 4. Acessar a API

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui
- **API Docs**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/actuator/health

## 🐳 Docker

### Build da Imagem

```bash
docker build -t spray-spring-api .
```

### Executar Container

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/tacdb \
  -e SPRING_DATASOURCE_USERNAME=tacdb_owner \
  -e SPRING_DATASOURCE_PASSWORD=npg_lUXQNZzx2J1d \
  spray-spring-api
```

## 📋 Endpoints Principais

### Autenticação
- `POST /auth/login` - Login com Cognito
- `POST /auth/refresh` - Renovar token de acesso
- `POST /auth/validate` - Validar token

### Aplicações
- `GET /api/aplicacoes` - Listar aplicações (paginado)
- `POST /api/aplicacoes` - Criar aplicação
- `GET /api/aplicacoes/{id}` - Buscar por ID
- `PUT /api/aplicacoes/{id}` - Atualizar aplicação
- `DELETE /api/aplicacoes/{id}` - Deletar aplicação
- `PATCH /api/aplicacoes/{id}/status` - Atualizar status
- `GET /api/aplicacoes/talhao/{id}` - Aplicações por talhão
- `GET /api/aplicacoes/equipamento/{id}` - Aplicações por equipamento

### Usuários
- `GET /api/usuarios` - Listar usuários
- `POST /api/usuarios` - Criar usuário
- `GET /api/usuarios/{id}` - Buscar por ID
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `DELETE /api/usuarios/{id}` - Deletar usuário

### Equipamentos
- `GET /api/equipamentos` - Listar equipamentos
- `POST /api/equipamentos` - Criar equipamento
- `GET /api/equipamentos/{id}` - Buscar por ID
- `PUT /api/equipamentos/{id}` - Atualizar equipamento

### Talhões
- `GET /api/talhoes` - Listar talhões
- `POST /api/talhoes` - Criar talhão
- `GET /api/talhoes/{id}` - Buscar por ID
- `PUT /api/talhoes/{id}` - Atualizar talhão

### Tipos de Aplicação
- `GET /api/tipos-aplicacao` - Listar tipos
- `POST /api/tipos-aplicacao` - Criar tipo
- `GET /api/tipos-aplicacao/{id}` - Buscar por ID
- `PUT /api/tipos-aplicacao/{id}` - Atualizar tipo

## 🔒 Autenticação e Segurança

### AWS Cognito Integration

A API integra com AWS Cognito para autenticação:

1. **Login**: Usuário faz login via `/auth/login` com username/password
2. **Token**: Recebe AccessToken, IdToken e RefreshToken
3. **Autorização**: Todas as rotas `/api/*` requerem Bearer token
4. **Refresh**: Token pode ser renovado via `/auth/refresh`

### JWT Token Provider

- Validação de tokens Cognito
- Extração de claims do usuário
- Verificação de assinatura via JWK

### Configuração de CORS

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    return source;
}
```

## 🗄️ Modelo de Dados

### Entidades Principais

#### Aplicacao
```java
{
  "id": "UUID",
  "talhao": "Talhao",
  "equipamento": "Equipamento", 
  "tipoAplicacao": "TipoAplicacao",
  "dataInicio": "LocalDateTime",
  "dataFim": "LocalDateTime",
  "finalizada": "Boolean",
  "observacoes": "String"
}
```

#### Usuario
```java
{
  "id": "UUID",
  "nome": "String",
  "email": "String",
  "ativo": "Boolean"
}
```

#### Equipamento
```java
{
  "id": "UUID",
  "nome": "String",
  "modelo": "String",
  "ativo": "Boolean"
}
```

## 🧪 Testes

### Executar Testes

```bash
# Testes unitários
mvn test

# Testes de integração
mvn verify

# Com cobertura
mvn test jacoco:report
```

### Testes HTTP

O projeto inclui arquivos `.http` para testes manuais em `src/test/java/br/edu/utfpr/api1/restclient/`:

- `auth.http` - Testes de autenticação
- `aplicacao.http` - Testes de aplicações
- `usuario.http` - Testes de usuários
- `equipamento.http` - Testes de equipamentos

## 🔧 Desenvolvimento

### Profiles

- **default**: Desenvolvimento local
- **docker**: Execução em container
- **prod**: Produção

### Logging

```properties
# Nível de log
logging.level.org.springframework.web.client.RestTemplate=DEBUG
logging.level.root=INFO

# Log de SQL
spring.jpa.show-sql=true
```

### Hot Reload

```bash
# Com Spring Boot DevTools
mvn spring-boot:run
# Mudanças são recarregadas automaticamente
```

## 📊 Monitoramento

### Actuator Endpoints

- `/actuator/health` - Status da aplicação
- `/actuator/info` - Informações da aplicação
- `/actuator/metrics` - Métricas da aplicação

### Health Checks

A aplicação inclui health checks para:
- Banco de dados PostgreSQL
- Conectividade com AWS Cognito
- Status geral da aplicação

## 🐛 Troubleshooting

### Problemas Comuns

1. **Erro de conexão com banco**
   ```bash
   # Verificar se PostgreSQL está rodando
   docker compose -f ../compose.dev.yaml logs postgres
   ```

2. **Erro de autenticação Cognito**
   ```bash
   # Verificar configurações no application.properties
   # Verificar logs da aplicação
   docker compose -f ../compose.dev.yaml logs spring-api
   ```

3. **Erro de CORS**
   ```bash
   # Verificar origem da requisição
   # Verificar configuração de CORS no SecurityConfig
   ```

### Logs Úteis

```bash
# Ver logs da aplicação
docker compose -f ../compose.dev.yaml logs -f spring-api

# Ver logs específicos de autenticação
docker compose -f ../compose.dev.yaml logs spring-api | grep -i "auth\|cognito"

# Ver logs de banco de dados
docker compose -f ../compose.dev.yaml logs spring-api | grep -i "sql\|jpa"
```

## 📝 Contribuição

1. Siga as convenções de código Java/Spring Boot
2. Implemente testes para novas funcionalidades
3. Documente endpoints no Swagger
4. Mantenha logs informativos
5. Valide entrada de dados adequadamente

## 🔗 Links Úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://spring.io/projects/spring-security)
- [AWS Cognito Integration](https://docs.aws.amazon.com/cognito/)
- [OpenAPI Specification](https://swagger.io/specification/)

---

**Nota**: Este backend faz parte do sistema completo de gerenciamento de pulverização. Para executar o sistema completo, veja o README principal do projeto.