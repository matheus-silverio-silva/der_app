# Sistema de Gerenciamento de Alunos e Cursos

Este projeto é uma aplicação console em Java que permite o cadastro e gerenciamento de **alunos**, **cursos** e **matrículas**, utilizando **Hibernate ORM** para persistência dos dados em um banco de dados MySQL.

## 📚 Funcionalidades

- ✅ Cadastrar aluno
- ✅ Cadastrar curso
- ✅ Matricular aluno em curso
- ✅ Cancelar matrícula
- ✅ Excluir aluno (com verificação de matrículas)
- ✅ Excluir curso (com verificação de alunos matriculados)
- ✅ Listar todos os alunos
- ✅ Listar cursos com alunos matriculados

## 🛠️ Tecnologias Utilizadas

- Java 17+ (funciona com JDK 22 também)
- Hibernate 5.6.15.Final
- MySQL
- Maven

## 🧱 Estrutura do Projeto

```
src/
├── com.example.app/
│   ├── MainApp.java           # Classe principal com menu interativo
│
├── com.example.app.dao/
│   └── GenericDAO.java        # Classe genérica de acesso ao banco
│
├── com.example.app.entity/
│   ├── Aluno.java             # Entidade Aluno
│   ├── Curso.java             # Entidade Curso
│   └── Matricula.java         # Entidade de associação Aluno-Curso
│
├── com.example.app.service/
│   ├── AlunoService.java
│   ├── CursoService.java
│   └── MatriculaService.java
```

## ⚙️ Configuração do Banco de Dados

O projeto está configurado para usar o MySQL com as seguintes credenciais (modifique conforme necessário):

```xml
<!-- Arquivo: hibernate.cfg.xml -->
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/der_app</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">sua_senha</property>
```

O Hibernate criará automaticamente as tabelas com base nas entidades se estiver com `hbm2ddl.auto = update`.

## ▶️ Executando o Projeto

1. Clone o repositório.
2. Certifique-se de que o MySQL está rodando e o banco `der_app` existe.
3. Importe o projeto no IntelliJ IDEA (ou outra IDE com suporte a Maven).
4. Execute a classe `MainApp.java`.

## 🚨 Observações

- O Hibernate está configurado com pool de conexão interno (não recomendado para produção).
- Todas as ações são executadas no console via `Scanner`.

## 📄 Licença

Projeto desenvolvido para fins educacionais.
