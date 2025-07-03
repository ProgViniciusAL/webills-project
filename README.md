# Webills gestão financeira (em desenvolvimento)

API REST desenvolvida com Spring Boot para servir como backend de um sistema de gestão financeira pessoal. Este projeto permite o controle de contas, categorias, pagamentos e autenticação de usuários com suporte a múltiplos tenants (usuários isolados).

---

## 🔧 Tecnologias Utilizadas

- Java 17+
- Spring Boot
  - Spring Web
  - Spring Data JPA
  - Spring Security
- PostgreSQL
- Maven
- Hibernate
- JWT
- JUnit (para testes)
- Lombok

---

### 📂 Principais Pacotes

- `account`: Gestão de contas dos usuários
- `bill`: Lançamentos financeiros (contas a pagar e receber)
- `category`: Classificação de contas (alimentação, transporte, etc.)
- `payment`: Pagamentos associados a lançamentos
- `service`: Regras de negócio (ex: validação de propriedade de categorias)
- `controller`: Endpoints REST
- `repository`: Interfaces de acesso a dados (JPA)

---

## 🛠️ Funcionalidades

- Cadastro e autenticação de usuários
- Criação de contas/categorias por usuário
- Lançamento de contas (bills)
- Associação de pagamentos a contas
- Multi-tenant: usuários só podem acessar seus próprios dados
- Paginação e ordenação (opcional)
- Segurança com sessões ou JWT

---

## ⚙️ Como rodar em sua máquina
# Clone o repositório
git clone https://github.com/ProgViniciusAL/webills-project.git
# Acesse o projeto
cd webills-project
# Configure o banco no application.properties
# Execute com Maven
mvn spring-boot:run


