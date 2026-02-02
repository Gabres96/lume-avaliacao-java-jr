# 🚀 Sistema de Gestão de Clientes

Esta é uma solução Fullstack. O projeto consiste em um ecossistema completo para gestão de clientes.

---

## 📌 Sobre o Projeto

O sistema permite o gerenciamento completo de clientes (CRUD), integrando uma API desenvolvida em **Java 21** com um frontend moderno em **React**.

**Principais destaques:**
* 🔐 Autenticação segura via **JWT** (Access & Refresh Token)
* 📍 Integração com **ViaCEP** para preenchimento automático de endereços.
* 🐳 Containerização total com **Docker**.
* 📖 Documentação interativa com **Swagger UI**.

### 🛠️ Pré-requisitos (O que instalar)
Para rodar esta aplicação em sua máquina, você precisará de:
* **Docker & Docker Desktop**: Necessário para subir o ambiente completo via containers (recomendado).
* **Node.js (v18 ou superior)**: Necessário para executar o frontend localmente.
* **Java 21 JDK**: Necessário caso opte por rodar o backend fora do Docker.
* **Git**: Para clonar o repositório.
* **IntelliJ**: Ambiente de desenvolvimento.

---

## 📥 1. Como Clonar o Repositório

- Pelo terminal, entre na pasta onde deseja clonar o repositório.

Para copiar localmente o projeto, execute o comando abaixo no seu terminal:

```bash
- git clone https://github.com/Gabres96/crud-cadastro.git
```
---

## ⚙️ 2. Como Executar o Projeto

O projeto está dividido entre `backend` (Java/Spring) e `frontend` (React).

### **2.1. Execução do Backend (Docker - Recomendado)**

Esta é a forma mais rápida de rodar a API

1.  **Certifique-se** de que o **Docker Desktop** está rodando em sua máquina.
2.  **Acesse a pasta do backend que está dentro do projeto que voce copiou**:
    ```bash
    cd backend
    ```
3.  **Construa a imagem Docker**:
    ```bash
    docker compose up --build
    ```
   

### **2.2. Execução do Frontend (Local)**

Para rodar a interface visual em ambiente de desenvolvimento, abra outro terminal( não feche o terminal que está rodando o docker(backend) e siga os passos abaixo:

1.  **Acesse a pasta do frontend**:
    ```bash
    cd ../frontend
    ```
2.  **Instale as dependências** do Node.js:
    ```bash
    npm install axios react-router-dom
    ```
3.  **Inicie o servidor de desenvolvimento**:
    ```bash
    npm start
    ```
---
## 📖 3. Documentação da API (Swagger)

A API utiliza o **Swagger UI** para fornecer uma documentação interativa e detalhada de todos os endpoints disponíveis. 

Com o backend em execução (seja via Docker ou Maven), você pode visualizar os modelos de dados, os contratos de entrada/saída (DTOs) e realizar testes em tempo real:

🔗 **Acesse aqui:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### **Recursos disponíveis no Swagger:**
* **Autenticação**: Endpoints para registro, login e refresh token.
* **Clientes**: Operações completas de CRUD com validação de campos.
* **Schemas**: Visualização da estrutura das entidades Usuário, Cliente e Tokens.

---

## 🔑 4. Credenciais de Teste e Acesso

Para validar o fluxo de segurança e o acesso ao CRUD de clientes, utilize as credenciais de exemplo abaixo:

* **E-mail:** `admin@admin.com`
* **Senha:** `123456`

> **⚠️ Nota:** O acesso ao CRUD é restrito. Conforme os requisitos, apenas usuários autenticados via **Access Token (JWT)** podem visualizar ou manipular dados de clientes.

### 4.1. **Como testar a aplicação via swaggerUI usando as credenciais admin:**
* Certifique-se que o backend está rodando.
* Acesse a URL do Swagger http://localhost:8080/swagger-ui/index.html#/
* Encontre a rota POST /auth/login, expanda e clique em try it out.
* Use as credenciais informadas acima  clique em execute.
* Abaixo no Server response, o status code deve ser 200 e no response body, você precisa copiar o token fornecido.
* Na página do Swagger, suba até o inicio dela e clique no botão no lado direito com um cadeado escrito "Authorize".
* Dentro do campo Value, cole o token copiado e clique em Authorize e depois em close. Agora logado com a credencial admin, voce poderá utilizar as rotas disponíveis para cadastro de cliente.

### 4.2. **Como testar a aplicação via swaggerUI criando um cadastro novo:**
* Certifique-se que o backend está rodando.
* Acesse a URL do Swagger http://localhost:8080/swagger-ui/index.html#/
* Encontre a rota POST /auth/register, expanda e clique em try it out.
* preencha as informações necessárias dentro das aspas, name, email e password e clique em execute.
* Encontre a rota POST /auth/login, expanda e clique em try it out.
* Use as credenciais que voce acabou de criar (apenas email e senha) e clique em execute.
* Abaixo no Server response, o status code deve ser 200 e no response body, você precisa copiar o token fornecido.
* Na página do Swagger, suba até o inicio dela e clique no botão no lado direito com um cadeado escrito "Authorize".
* Dentro do campo Value, cole o token copiado e clique em Authorize e depois em close. Agora logado, voce poderá utilizar as rotas disponíveis para cadastro de cliente.

### 4.3. **Testar a aplicação usando a tela de login:**
* Certifique-se que que o backend e o frontend estejam rodando.
* Acesse  pelo seu navegador http://localhost:3000.
* Utilize as credencias admin ou crie um novo cadastro para fazer login.
* Agora logado, através da tela voce poderá fazer todas as operações para cadastrar informações.

---
## 🚀 5. Instruções Adicionais

Para facilitar a auditoria e o acompanhamento do desenvolvimento, utilize as informações abaixo:

### **🔍 Inspeção de Dados (H2 Console)**
Caso deseje visualizar as tabelas do banco de dados em memória diretamente, o console do H2 está habilitado:
* **URL**: `http://localhost:8080/h2-console`
* **JDBC URL**: `jdbc:h2:mem:lumedb`
* **Connect**
---

## 🛡️ 6. Funcionalidades e Requisitos Técnicos

### **⚙️ Backend (Spring Boot 3 + Java 21)**
* **Segurança**: Implementação de **Spring Security** com autenticação via **JWT**, incluindo suporte a **Access Token** e **Refresh Token**.
* **Persistência**: Utilização de **Spring Data JPA** com banco de dados **H2** em memória, facilitando o teste imediato sem configurações complexas de DB.
* **Validação de CPF**: Lógica customizada para validar tanto o formato quanto o dígito verificador, garantindo a integridade dos dados.
* **Integração ViaCEP**: Busca automática de endereço (logradouro, bairro, cidade, estado) a partir do CEP informado, utilizando integração com serviço externo.
* **Arquitetura**: Uso de **DTOs** (Data Transfer Objects) para entrada e saída de dados, além de validações com **Bean Validation**.

### **💻 Frontend (React)**
* **Interface**: Telas de **Login** e **CRUD de Clientes** com design simples e funcional.
* **Proteção de Rotas**: Implementação de rotas protegidas que exigem autenticação ativa para acessar o gerenciamento de clientes.
* **Integração**: Consumo completo da API Backend para as operações de Criar, Buscar, Listar, Atualizar e Deletar clientes.

---

### **👨‍💻 Desenvolvedor**
* **Responsável**: Gabriel (@Gabres96)
---
