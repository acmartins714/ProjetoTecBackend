# IESPFLIX - Plataforma de Streaming (Backend & Frontend)

# Aluno: Alexandre Chaves Martins
# Professor: Rodrigo Fujioka
# Disciplica: Tecnologia para Backend 3º Período - 2026.1

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green?style=for-the-badge&logo=springboot)
![React](https://img.shields.io/badge/React-19-blue?style=for-the-badge&logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue?style=for-the-badge&logo=postgresql)

Este projeto é uma plataforma completa de streaming desenvolvida como parte da disciplina de **Tecnologia para Backend** no curso de Sistemas de Informação da **UNIESP**. O sistema engloba desde a gestão de catálogo de conteúdos até o controle de assinaturas e usuários.

---

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 21** (LTS)
- **Spring Boot 3.5.10**
- **Spring Data JPA** - Persistência de dados e consultas JPQL.
- **Spring Web** - Desenvolvimento de APIs RESTful.
- **Spring Cloud OpenFeign** - Integração com APIs externas (BrasilAPI, ViaCep).
- **Spring Validation** - Validação de dados com Custom Validators.
- **SpringDoc OpenAPI (Swagger)** - Documentação automatizada da API.
- **Lombok** - Redução de código boilerplate.
- **ModelMapper** - Mapeamento entre Entidades e DTOs.
- **Flyway** - Gerenciamento de migrações de banco de dados.
- **H2 Database** - Banco de dados em memória para desenvolvimento.
- **PostgreSQL** - Banco de dados recomendado para homologação/produção.
- **JUnit 5 & Mockito** - Testes unitários e de integração.
- **JaCoCo** - Relatórios de cobertura de código.

### Frontend
- **React 19**
- **Axios** - Consumo de APIs.
- **Context API** - Gerenciamento de estado global (Tema, Autenticação).

---

## 🛠️ Arquitetura do Sistema

O projeto segue os princípios da **Arquitetura em Camadas**, garantindo separação de responsabilidades e facilidade de manutenção:

- **Controller**: Portas de entrada da aplicação, lidam com requisições HTTP.
- **Service**: Camada de lógica de negócio e orquestração.
- **Repository**: Abstração de acesso ao banco de dados (Spring Data JPA).
- **Model/Entity**: Representação das tabelas do banco de dados.
- **DTO (Data Transfer Object)**: Objetos para transferência de dados entre camadas e API.
- **Mapper**: Conversão bidirecional entre Entidades e DTOs.
- **Validation**: Regras de integridade de dados e validadores customizados (CPF, CNPJ, Senha Forte).

---

## 📋 Especificações Funcionais (Entidades)

### 1. Usuários (`usuarios`)
- Cadastro completo com Nome, Data de Nascimento, E-mail (único), CPF/CNPJ e Perfil (ADMIN/USER).
- Senhas armazenadas de forma segura (Hash BCrypt).

### 2. Catálogo de Conteúdo (`conteudo` & `filme`)
- Gerenciamento de Filmes e Séries.
- Atributos: Título, Ano (1888-2100), Duração, Relevância, Sinopse, Trailer URL e Gênero.

### 3. Favoritos (`favorito`)
- Permite que usuários salvem seus conteúdos preferidos.
- PK Composta e relacionamento Muitos-para-Muitos.

### 4. Assinaturas e Planos (`assinatura` & `plano`)
- Planos predefinidos: BÁSICO, PADRÃO, PREMIUM.
- Controle de status de assinatura: ATIVA, EM_ATRASO, CANCELADA.

### 5. Métodos de Pagamento (`metodo_pagamento`)
- Armazenamento tokenizado de cartões (Bandeira, Últimos 4 dígitos, Validade, Nome do Portador).

---

## 🔍 Consultas Avançadas (JPQL)
A API implementa diversas consultas customizadas, incluindo:
1. Ordenação de conteúdos por título.
2. Filtro por gênero (case-insensitive).
3. Busca dos Top N conteúdos por relevância.
4. Listagem de conteúdos lançados após determinado ano.
5. Contagem de assinaturas ativas por plano.
6. Busca por palavra-chave em títulos e sinopses.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
- Java 21+
- Maven 3.9+
- Node.js 18+ (para o frontend)

### Backend
1. Clone o repositório:
   ```bash
   git clone https://github.com/usuario/projeto-tec-backend.git
   ```
2. Navegue até a raiz do projeto e execute:
   ```bash
   mvn clean spring-boot:run
   ```
3. A API estará disponível em `http://localhost:8080`.

### Frontend
1. Navegue até a pasta do frontend:
   ```bash
   cd tecFrontend
   ```
2. Instale as dependências:
   ```bash
   npm install
   ```
3. Inicie a aplicação:
   ```bash
   npm start
   ```
4. O frontend estará disponível em `http://localhost:3000`.

---

## 📖 Documentação e Ferramentas

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **H2 Console**: [http://localhost:8080/h2](http://localhost:8080/h2)
  - JDBC URL: `jdbc:h2:file:~/teckback`
  - User: `sa` | Password: (vazio)

---

## 🧪 Testes e Qualidade
Para executar a suíte de testes e gerar o relatório de cobertura JaCoCo:
```bash
mvn test jacoco:report
```
O relatório será gerado em: `target/site/jacoco/index.html`.

---

## 👥 Integrantes
- **Alexandre Chaves Martins** - Matrícula: 2025111510309

---

## 👨‍🏫 Professor
- **Rodrigo Fujioka**

---
© 2026 IESPFLIX. Desenvolvido para fins acadêmicos.
