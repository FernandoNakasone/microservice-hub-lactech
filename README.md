## Alterações na solução

Durante a implementação, algumas decisões foram ajustadas em relação ao modelo inicialmente apresentado no ArchiMate.

* **Número de telefone:** no ArchiMate, o número de telefone do usuário estava previsto em um banco de dados separado. Durante a implementação, verificamos que essa separação não seria necessária para o funcionamento da solução. Dessa forma, o número de telefone foi incorporado à tabela de usuários, mantendo os dados relacionados ao usuário centralizados.

* **Sistema de notificações:** inicialmente estava prevista a implementação de um sistema de notificações. Como não definimos uma implementação adequada para esse recurso dentro do escopo deste Sprint, optamos por não implementá-lo nesta versão da solução.

## Configuração do projeto

Para executar o projeto, é necessário possuir o **Java 25** instalado.

No IntelliJ IDEA, acesse **Project Structure** e configure:

* **Project SDK:** Java 25
* **Language Level:** 25 – Compact source files, etc.

### Configuração da API do Gemini

No arquivo `application.properties` do projeto **lacty**, configure a API Key do Gemini fornecida no arquivo `.txt` enviado junto à Sprint.

> **Importante:** a API Key não deve ser publicada diretamente no repositório do GitHub pois se publicada o bot da google identifica a chave e deleta ela para evitar que bots maliciosos tenham acesso a ela. Utilize a chave fornecida pela equipe no arquivo .txt enviado na entrega da sprint.

### Ordem de inicialização

Para garantir o funcionamento correto da arquitetura de microsserviços, execute os projetos na seguinte ordem:

1. `eureka-server`
2. `api-gateway`
3. `lacty`
4. `performance-chatbot`
5. `gestor`
6. `performance-website`

O `eureka-server` deve estar em execução antes dos demais serviços para que os microsserviços possam ser registrados e descobertos corretamente.

## Swagger / OpenAPI

Os endpoints da aplicação podem ser testados utilizando o Swagger através do API Gateway.

### Lacty

**Swagger UI:**

`http://localhost:8082/ms-lacty/swagger-ui/index.html`

**OpenAPI:**

`/ms-lacty/v3/api-docs`

### Performance Chatbot

**Swagger UI:**

`http://localhost:8082/ms-performance-chatbot/swagger-ui/index.html`

**OpenAPI:**

`/ms-performance-chatbot/v3/api-docs`

### Performance Website

**Swagger UI:**

`http://localhost:8082/ms-performance-website/swagger-ui/index.html`

**OpenAPI:**

`/ms-performance-website/v3/api-docs`

### Gestor

**Swagger UI:**

`http://localhost:8082/ms-gestor/swagger-ui/index.html`

**OpenAPI:**

`/ms-gestor/v3/api-docs`
