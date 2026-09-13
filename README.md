# Sistema de Gerenciamento de Autoescola (Autoescola 3ESPH)

#Integrantes

Djalma RM555530

Felipe RM558447

Otávio RM556452

## Tecnologias Utilizadas

* **Java 17**
* **Spring Boot** (Web, Data JPA, Security, Validation)
* **Spring Security com JSON Web Token (JWT)** para autenticação e autorização
* **Hibernate / Flyway** para persistência e controle de migrações de banco de dados
* **MySQL** como SGBD relacional
* **Maven** para gerenciamento de dependências

---

## 📋 Funcionalidades do Sistema

* **Gestão de Usuários (Acesso Restrito a Administradores):**
  * Cadastro, listagem, atualização de perfil e exclusão de usuários do sistema.
  * Sistema de encriptação de senha utilizando **BCrypt**.
  * Alteração de senha para usuários já autenticados.
* **Gestão de Alunos:**
  * Cadastro com validação estrita de CPF e CEP.
  * Listagem paginada (10 registros por página) e ordenada crescentemente por nome.
  * Atualização cadastral (preservando o e-mail e o CPF).
  * Exclusão lógica (o aluno é marcado como "inativo" no sistema em vez de ser removido do banco).
* **Gestão de Instrutores:**
  * Cadastro obrigatório de dados, CNH e Especialidades (*Motos, Carros, Vans e Caminhões*).
  * Listagem paginada e ordenada por nome.
  * Atualização cadastral (com restrição de alteração para e-mail, CNH e especialidade).
  * Exclusão lógica (inatividade).
* **Agendamento e Cancelamento de Instruções:**
  * Validação do horário de funcionamento da autoescola (segunda a sábado, das 06:00 às 21:00).
  * Aulas com duração fixa de 1 hora e antecedência mínima de 30 minutos para agendamento.
  * Restrição contra alunos ou instrutores inativos.
  * Bloqueio para mais de duas instruções no mesmo dia para um mesmo aluno ou conflito de horário para o instrutor.
  * Atribuição automática de instrutor disponível caso a escolha seja opcional.
  * Cancelamento de aulas condicionado ao preenchimento de motivo pré-definido e antecedência mínima de 24 horas.

---

## 🔒 Segurança

A aplicação implementa uma camada robusta de segurança baseada em **Spring Security** e **tokens JWT**:
* **Autenticação Stateless:** As requisições para rotas protegidas exigem o envio de um token JWT válido no cabeçalho HTTP (`Authorization: Bearer <TOKEN>`).
* **Proteção de Senhas:** Nenhuma senha é armazenada em texto plano no banco de dados; todas passam por um processo de hash unidirecional utilizando o algoritmo **BCrypt**.
* **Controle de Acessos (RBAC):** Endpoints sensíveis de gerenciamento de usuários são restritos exclusivamente a perfis com privilégios de **ADMINISTRADOR**, garantindo que operações administrativas fiquem isoladas e seguras.

---

## ⚙️ Pré-requisitos para Execução

* **Java JDK 17** ou superior instalado.
* **Maven** configurado.
* Servidor **MySQL** rodando localmente (ou via container Docker).

---

## 🔧 Configuração e Execução

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/OTAVIO48FERRAO/autoescola3esph.git](https://github.com/OTAVIO48FERRAO/autoescola3esph.git)
   cd autoescola3esph
   ```


Configure o Banco de Dados:
Crie um banco de dados MySQL chamado autoescola3esph:

```
SQL
CREATE DATABASE autoescola3esph;
```

Ajuste as credenciais:
Abra o arquivo src/main/resources/application.properties (ou application.yml) e informe o seu usuário e senha do MySQL:

```
Properties
spring.datasource.url=jdbc:mysql://localhost:3306/autoescola3esph?useTimezone=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sua_senha_aqui
```

Execute a aplicação:
Utilize o Maven para compilar e iniciar o projeto:

```
Bash
mvn spring-boot:run
```

Testando via Postman:

Faça uma requisição POST para /login enviando as credenciais de administrador para obter o seu token JWT.

Insira o token obtido no formato Bearer Token na aba de Autorização do Postman para testar os demais endpoints protegidos (/alunos, /instrutores, etc.).
