# LibraryAPI

Projeto de estudo desenvolvido durante o módulo de **Spring Data JPA**, com foco em mapeamento de entidades, relacionamentos e diferentes formas de consulta com Spring Data (Query Methods, JPQL e parâmetros nomeados/posicionais).

> ⚠️ Este projeto tem propósito didático: não expõe endpoints REST (sem controllers). A interação acontece via repositórios e um `Service` de exemplos, executados/testados diretamente no código.

## 🛠 Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Hibernate (via Spring Data JPA)
- HikariCP (pool de conexões, configurado manualmente)
- Lombok
- Maven

## 📁 Estrutura do projeto

```
src/main/java/io/github/LibraryAPI/
├── Application.java              # Classe principal (Spring Boot)
├── config/
│   └── DatabaseConfiguration.java   # DataSource configurado manualmente com HikariCP
├── model/
│   ├── Autor.java                # Entidade Autor (1:N com Livro)
│   ├── Livro.java                # Entidade Livro (N:1 com Autor)
│   └── GeneroLivro.java          # Enum de gêneros literários
├── repository/
│   ├── AutorRepository.java
│   └── LivroRepository.java      # Query Methods, JPQL, @Modifying, parâmetros nomeados/posicionais
└── service/
    └── TransacaoService.java     # Exemplos de uso de @Transactional
```

## 🗃 Modelo de dados

- **Autor**: `id (UUID)`, `nome`, `data_nascimento`, `nacionalidade`
- **Livro**: `id (UUID)`, `isbn`, `titulo`, `data_lancamento`, `genero (enum)`, `preco`, `autor (FK)`

Relacionamento: um `Autor` possui vários `Livro` (`@OneToMany` / `@ManyToOne`).

## 🔍 Destaques do repositório `LivroRepository`

O arquivo concentra os principais exemplos estudados no módulo:

- **Query Methods**: `findByAutor`, `findByTitulo`, `findByTituloAndPreco`, `findByTituloOrIsbn`, `findByTituloContainingIgnoreCase`
- **JPQL** com `@Query`: ordenação, `join`, `distinct`, filtros por atributos relacionados
- **Parâmetros nomeados** (`@Param`) e **posicionais** (`?1`, `?2`)
- **Operações de escrita** com `@Modifying` + `@Transactional` (`delete`, `update`)

## ▶️ Como rodar localmente

### 1. Subir o banco de dados (Docker)

```bash
docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library postgres:18.6
```

Opcionalmente, subir também o PgAdmin4:

```bash
docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin dpage/pgadmin4:9.17
```

Os comandos completos estão em [`comandos-docker.txt`](comandos-docker.txt).

### 2. Configurar a conexão

A conexão já está definida em `src/main/resources/application.yaml` apontando para o banco criado acima (`localhost:5432/library`, usuário/senha `postgres`). Ajuste se necessário.

O schema das tabelas está disponível em [`comandos-sql.txt`](comandos-sql.txt) (o Hibernate também pode criá-las automaticamente, já que `ddl-auto: update` está ativo).

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```
