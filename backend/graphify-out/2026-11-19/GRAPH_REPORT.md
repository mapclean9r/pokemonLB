# Graph Report - backend  (2026-11-19)

## Corpus Check
- 78 files · ~3,299,276 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 2 file(s) not represented in the graph (top: .properties 1, .cmd 1)

## Summary
- 452 nodes · 1223 edges · 21 communities (14 shown, 7 thin omitted)
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 64 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `955d0370`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- FindById
- org.springframework.web.bind.annotation.GetMapping
- JPA Entity Mappings
- Data Injection Runner
- Move
- PokemonlbApplicationTests.java
- org.junit.jupiter.api.Test
- org.springframework.data.jpa.repository.JpaRepository
- FindEvolutions
- PokemonControllerTest.java
- Application Config (YAML)
- Docker Postgres Setup
- PokemonService.java
- Global Exception Handler
- App Entry Point
- Maven Project Coordinates
- org.junit.jupiter.api.Nested
- PokemonControllerTest
- com.pokemonlb.model.entity.Pokemon
- com.pokemonlb.model.entity.Type

## God Nodes (most connected - your core abstractions)
1. `GameDto` - 20 edges
2. `PokemonService` - 20 edges
3. `GameService` - 16 edges
4. `MoveGameAvailability` - 16 edges
5. `PokemonMove` - 16 edges
6. `ItemSummaryDto` - 16 edges
7. `NotFoundException` - 16 edges
8. `PokemonServiceEvolutionTest` - 15 edges
9. `Move` - 15 edges
10. `MoveService` - 13 edges

## Surprising Connections (you probably didn't know these)
- `PokemonServiceEvolutionTest` --references--> `PokemonService`  [EXTRACTED]
  src/test/java/com/pokemonlb/service/PokemonServiceEvolutionTest.java → src/main/java/com/pokemonlb/service/PokemonService.java
- `GameService` --references--> `PokemonRepository`  [EXTRACTED]
  src/main/java/com/pokemonlb/service/GameService.java → src/main/java/com/pokemonlb/repository/PokemonRepository.java
- `GameController` --references--> `GameService`  [EXTRACTED]
  src/main/java/com/pokemonlb/controller/GameController.java → src/main/java/com/pokemonlb/service/GameService.java
- `GameControllerTest` --references--> `GameController`  [EXTRACTED]
  src/test/java/com/pokemonlb/controller/GameControllerTest.java → src/main/java/com/pokemonlb/controller/GameController.java
- `ItemController` --references--> `ItemService`  [EXTRACTED]
  src/main/java/com/pokemonlb/controller/ItemController.java → src/main/java/com/pokemonlb/service/ItemService.java

## Import Cycles
- None detected.

## Communities (21 total, 7 thin omitted)

### Community 0 - "FindById"
Cohesion: 0.27
Nodes (3): PokemonDetailDto, PokemonStatsDto, FindById

### Community 1 - "org.springframework.web.bind.annotation.GetMapping"
Cohesion: 0.11
Nodes (11): ApiUrls, Games, Items, Moves, Pokemon, Types, GameController, ItemController (+3 more)

### Community 2 - "JPA Entity Mappings"
Cohesion: 0.13
Nodes (13): Game, ItemGameAvailability, Key, Key, MoveGameAvailability, MoveLearnMethod, Pokemon, Id (+5 more)

### Community 3 - "Data Injection Runner"
Cohesion: 0.09
Nodes (3): Config, DbDataInject, SqlScriptInjector

### Community 4 - "Move"
Cohesion: 0.20
Nodes (5): LearnsetEntryDto, MoveDto, Move, MoveRepository, MoveService

### Community 6 - "org.junit.jupiter.api.Test"
Cohesion: 0.10
Nodes (7): FindById, FindMoves, FindAll, FindAll, FindById, FindAll, FindById

### Community 7 - "org.springframework.data.jpa.repository.JpaRepository"
Cohesion: 0.08
Nodes (14): NotFoundException, GameDto, ItemDetailDto, ItemSummaryDto, MoveSummaryDto, ItemGenerationAvailability, Key, GameRepository (+6 more)

### Community 9 - "PokemonControllerTest.java"
Cohesion: 0.21
Nodes (4): GameControllerTest, ItemControllerTest, MoveControllerTest, TypeControllerTest

### Community 10 - "Application Config (YAML)"
Cohesion: 0.12
Nodes (15): name, password, url, username, ddl-auto, default_batch_fetch_size, format_sql, hibernate (+7 more)

### Community 11 - "Docker Postgres Setup"
Cohesion: 0.15
Nodes (12): POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, container_name, environment, image, ports, volumes (+4 more)

### Community 12 - "PokemonService.java"
Cohesion: 0.12
Nodes (3): MoveLevelByGenerationDto, PokemonMoveRepository, PokemonService

### Community 17 - "org.junit.jupiter.api.Nested"
Cohesion: 0.10
Nodes (7): FindAll, FindItems, FindPokemon, FindById, FindGames, FindAll, FindPokemon

### Community 18 - "PokemonControllerTest"
Cohesion: 0.17
Nodes (4): FindGames, FindLearnset, FindMoveLevels, PokemonControllerTest

### Community 19 - "com.pokemonlb.model.entity.Pokemon"
Cohesion: 0.08
Nodes (8): EvolutionDto, PokemonRefDto, PokemonSummaryDto, PokemonEvolution, PokemonEvolutionRepository, PokemonRepository, PokemonStatsRepository, PokemonServiceEvolutionTest

## Knowledge Gaps
- **1 isolated node(s):** `com.pokemonlb:pokemonlb`
  These have ≤1 connection - possible missing edges. (Counts symbols only; 68 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NotFoundException` connect `org.springframework.data.jpa.repository.JpaRepository` to `Move`, `org.junit.jupiter.api.Test`, `PokemonControllerTest.java`, `Global Exception Handler`, `org.junit.jupiter.api.Nested`?**
  _High betweenness centrality (0.046) - this node is a cross-community bridge._
- **What connects `com.pokemonlb:pokemonlb` to the rest of the system?**
  _1 weakly-connected nodes found - possible missing edges._
- **Should `org.springframework.web.bind.annotation.GetMapping` be split into smaller, more focused modules?**
  _Cohesion score 0.11184939091915837 - nodes in this community are weakly interconnected._
- **Why does `PokemonService` connect `PokemonService.java` to `org.springframework.web.bind.annotation.GetMapping`, `com.pokemonlb.model.entity.Pokemon`, `org.springframework.data.jpa.repository.JpaRepository`?**
  _High betweenness centrality (0.028) - this node is a cross-community bridge._
- **Should `JPA Entity Mappings` be split into smaller, more focused modules?**
  _Cohesion score 0.1286195286195286 - nodes in this community are weakly interconnected._
- **Should `Data Injection Runner` be split into smaller, more focused modules?**
  _Cohesion score 0.09113300492610837 - nodes in this community are weakly interconnected._
- **Should `org.junit.jupiter.api.Test` be split into smaller, more focused modules?**
  _Cohesion score 0.10344827586206896 - nodes in this community are weakly interconnected._