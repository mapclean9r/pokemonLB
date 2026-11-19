# Graph Report - backend  (2026-11-19)

## Corpus Check
- 86 files · ~3,305,753 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 2 file(s) not represented in the graph (top: .properties 1, .cmd 1)

## Summary
- 531 nodes · 1333 edges · 27 communities (19 shown, 8 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 84 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `68501f3e`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Pokémon API
- org.springframework.web.bind.annotation.GetMapping
- JPA Entity Mappings
- Data Injection Runner
- org.springframework.data.jpa.repository.JpaRepository
- com.pokemonlb.model.entity.Type
- org.junit.jupiter.api.Test
- NotFoundException
- FindEvolutions
- PokemonControllerTest.java
- Application Config (YAML)
- Docker Postgres Setup
- FindLearnset
- Global Exception Handler
- App Entry Point
- Maven Project Coordinates
- org.junit.jupiter.api.Nested
- FindById
- com.pokemonlb.model.entity.Pokemon
- API
- Games API
- Moves API
- docker-compose.yml
- Types API
- Items API

## God Nodes (most connected - your core abstractions)
1. `PokemonService` - 20 edges
2. `GameDto` - 20 edges
3. `PokemonMove` - 17 edges
4. `ItemSummaryDto` - 16 edges
5. `NotFoundException` - 16 edges
6. `MoveGameAvailability` - 16 edges
7. `GameService` - 16 edges
8. `PokemonServiceEvolutionTest` - 15 edges
9. `Move` - 15 edges
10. `PokemonController` - 14 edges

## Surprising Connections (you probably didn't know these)
- `Application layers (`com.pokemonlb`)` --references--> `GlobalExceptionHandler`  [INFERRED]
  CLAUDE.md → src/main/java/com/pokemonlb/exception/GlobalExceptionHandler.java
- `Games API` --references--> `GameController`  [INFERRED]
  docs/api/games.md → src/main/java/com/pokemonlb/controller/GameController.java
- `Items API` --references--> `ItemController`  [INFERRED]
  docs/api/items.md → src/main/java/com/pokemonlb/controller/ItemController.java
- `Moves API` --references--> `MoveController`  [INFERRED]
  docs/api/moves.md → src/main/java/com/pokemonlb/controller/MoveController.java
- `Pokémon API` --references--> `PokemonController`  [INFERRED]
  docs/api/pokemon.md → src/main/java/com/pokemonlb/controller/PokemonController.java

## Import Cycles
- None detected.

## Communities (27 total, 8 thin omitted)

### Community 0 - "Pokémon API"
Cohesion: 0.12
Nodes (17): A Pokémon that doesn't evolve: Tauros, All Pokémon, Branching: Eevee, Errors, Evolution family, Games a Pokémon is in, Generation 4 Pokémon in Legends: Arceus, Get one Pokémon (+9 more)

### Community 1 - "org.springframework.web.bind.annotation.GetMapping"
Cohesion: 0.06
Nodes (25): API documentation, Before you start, Conventions, Errors, ApiUrls, Games, Items, Moves (+17 more)

### Community 2 - "JPA Entity Mappings"
Cohesion: 0.11
Nodes (17): Application layers (`com.pokemonlb`), Game, ItemGameAvailability, Key, ItemGenerationAvailability, Key, Key, MoveGameAvailability (+9 more)

### Community 3 - "Data Injection Runner"
Cohesion: 0.09
Nodes (4): Schema and data are deliberately separate, Config, DbDataInject, SqlScriptInjector

### Community 4 - "org.springframework.data.jpa.repository.JpaRepository"
Cohesion: 0.11
Nodes (9): MoveDto, MoveLevelByGenerationDto, Move, GameRepository, MoveGameAvailabilityRepository, MoveRepository, PokemonMoveRepository, PokemonStatsRepository (+1 more)

### Community 6 - "org.junit.jupiter.api.Test"
Cohesion: 0.11
Nodes (7): FindAll, FindAll, FindAll, FindGames, FindMoveLevels, FindById, FindPokemon

### Community 7 - "NotFoundException"
Cohesion: 0.17
Nodes (4): NotFoundException, FindById, FindById, FindGames

### Community 9 - "PokemonControllerTest.java"
Cohesion: 0.21
Nodes (4): ItemControllerTest, MoveControllerTest, PokemonControllerTest, TypeControllerTest

### Community 10 - "Application Config (YAML)"
Cohesion: 0.12
Nodes (15): name, password, url, username, ddl-auto, default_batch_fetch_size, format_sql, hibernate (+7 more)

### Community 11 - "Docker Postgres Setup"
Cohesion: 0.22
Nodes (9): POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, container_name, environment, image, ports, volumes (+1 more)

### Community 17 - "org.junit.jupiter.api.Nested"
Cohesion: 0.12
Nodes (7): FindAll, FindById, FindItems, FindMoves, FindPokemon, GameControllerTest, FindAll

### Community 18 - "FindById"
Cohesion: 0.24
Nodes (3): PokemonDetailDto, PokemonStatsDto, FindById

### Community 19 - "com.pokemonlb.model.entity.Pokemon"
Cohesion: 0.07
Nodes (6): EvolutionDto, PokemonRefDto, PokemonEvolutionRepository, PokemonRepository, PokemonService, PokemonServiceEvolutionTest

### Community 20 - "API"
Cohesion: 0.08
Nodes (22): Architecture, Commands, Data model decisions that aren't obvious from one file, Project, Tests, 1. Start a database, 2. Fill the database (once), 3. Run the API (+14 more)

### Community 21 - "Games API"
Cohesion: 0.25
Nodes (8): Errors, Game ids, Games API, Get one game, Items in a game, List games, Moves in a game, Pokémon in a game

### Community 23 - "Moves API"
Cohesion: 0.29
Nodes (7): Errors, Games a move is in, Get one move, List moves, Move fields, Moves API, Only Electric moves

### Community 24 - "docker-compose.yml"
Cohesion: 0.50
Nodes (3): services, volumes, pgdata

### Community 25 - "Types API"
Cohesion: 0.33
Nodes (6): Errors, Get one type, List types, Pokémon of a type, Type ids, Types API

### Community 26 - "Items API"
Cohesion: 0.40
Nodes (5): Errors, Get one item, Items API, List items, Only Generation 9

## Knowledge Gaps
- **1 isolated node(s):** `com.pokemonlb:pokemonlb`
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 119 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **8 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PokemonController` connect `org.springframework.web.bind.annotation.GetMapping` to `Pokémon API`, `PokemonControllerTest.java`, `com.pokemonlb.model.entity.Pokemon`?**
  _High betweenness centrality (0.092) - this node is a cross-community bridge._
- **What connects `com.pokemonlb:pokemonlb` to the rest of the system?**
  _1 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Pokémon API` be split into smaller, more focused modules?**
  _Cohesion score 0.11764705882352941 - nodes in this community are weakly interconnected._
- **Why does `Pokémon API` connect `Pokémon API` to `org.springframework.web.bind.annotation.GetMapping`, `README.md`?**
  _High betweenness centrality (0.073) - this node is a cross-community bridge._
- **Should `org.springframework.web.bind.annotation.GetMapping` be split into smaller, more focused modules?**
  _Cohesion score 0.05750350631136045 - nodes in this community are weakly interconnected._
- **Why does `pokemonLB` connect `API` to `README.md`?**
  _High betweenness centrality (0.060) - this node is a cross-community bridge._
- **Should `JPA Entity Mappings` be split into smaller, more focused modules?**
  _Cohesion score 0.10855094726062468 - nodes in this community are weakly interconnected._