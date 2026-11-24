# Graph Report - pokemonLB  (2026-11-24)

## Corpus Check
- 106 files · ~13,581 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 6 file(s) not represented in the graph (top: (none) 4, .example 1, .css 1)

## Summary
- 661 nodes · 1734 edges · 27 communities (14 shown, 13 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 75 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a0a06fad`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- PokemonControllerTest
- PokemonService.java
- Pokemon
- Backend: JPA Entities
- Frontend: Config and Dependencies
- Frontend: Homepage, Services and Hooks
- list
- Backend: Data Injection Runner
- Frontend: TypeScript Config
- PokemonServiceEvolutionTest
- Backend: Application Config
- Backend: Docker Postgres
- SecurityConfig.java
- Backend: Exception Handling
- Backend: App Entry Point
- Backend: Maven Coordinates
- AbilityControllerTest
- org.springframework.data.jpa.repository.JpaRepository
- PokemonServiceEvolutionTest.java
- org.junit.jupiter.api.Test
- org.junit.jupiter.api.Nested
- PokemonSpecifications.java
- FindEvolutions
- PokemonlbApplicationTests.java

## God Nodes (most connected - your core abstractions)
1. `NotFoundException` - 27 edges
2. `Pokemon` - 25 edges
3. `GameDto` - 23 edges
4. `PokemonService` - 20 edges
5. `GameService` - 18 edges
6. `ItemSummaryDto` - 17 edges
7. `PokemonAbility` - 17 edges
8. `MoveGameAvailability` - 16 edges
9. `PokemonMove` - 16 edges
10. `PokemonSummaryDto` - 16 edges

## Surprising Connections (you probably didn't know these)
- `TypeControllerTest` --references--> `TypeController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/TypeControllerTest.java → backend/src/main/java/com/pokemonlb/controller/TypeController.java
- `TypeControllerTest` --references--> `TypeDto`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/TypeControllerTest.java → backend/src/main/java/com/pokemonlb/model/dto/TypeDto.java
- `TypeControllerTest` --references--> `TypeService`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/TypeControllerTest.java → backend/src/main/java/com/pokemonlb/service/TypeService.java
- `PokemonEvolutionRepository` --references--> `PokemonEvolution`  [EXTRACTED]
  backend/src/main/java/com/pokemonlb/repository/PokemonEvolutionRepository.java → backend/src/main/java/com/pokemonlb/model/entity/PokemonEvolution.java
- `PokemonServiceEvolutionTest` --references--> `PokemonService`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/service/PokemonServiceEvolutionTest.java → backend/src/main/java/com/pokemonlb/service/PokemonService.java

## Import Cycles
- None detected.

## Communities (27 total, 13 thin omitted)

### Community 0 - "PokemonControllerTest"
Cohesion: 0.15
Nodes (8): FindItems, GameControllerTest, FindById, ItemControllerTest, MoveControllerTest, FindAbilities, PokemonControllerTest, TypeControllerTest

### Community 2 - "Pokemon"
Cohesion: 0.10
Nodes (7): EvolutionDto, PokemonDetailDto, PokemonRefDto, PokemonStatsDto, Pokemon, PokemonRepository, FindById

### Community 3 - "Backend: JPA Entities"
Cohesion: 0.09
Nodes (21): PokemonAbilityDto, Ability, Game, ItemGameAvailability, Key, ItemGenerationAvailability, Key, Key (+13 more)

### Community 4 - "Frontend: Config and Dependencies"
Cohesion: 0.08
Nodes (31): eslintConfig, dependencies, next@16.4.6, react@19.3.0, react-dom@19.3.0, devDependencies, eslint@^9, eslint-config-next@16.4.6 (+23 more)

### Community 5 - "Frontend: Homepage, Services and Hooks"
Cohesion: 0.07
Nodes (44): nextConfig, GET(), toInteger(), lilita, metadata, nunito, ApiUnavailable(), Catalog() (+36 more)

### Community 6 - "list"
Cohesion: 0.05
Nodes (28): Abilities, ApiUrls, Games, Items, Moves, Pokemon, Types, AbilityController (+20 more)

### Community 7 - "Backend: Data Injection Runner"
Cohesion: 0.09
Nodes (3): Config, DbDataInject, SqlScriptInjector

### Community 8 - "Frontend: TypeScript Config"
Cohesion: 0.11
Nodes (18): compilerOptions, allowJs, esModuleInterop, incremental, isolatedModules, jsx, lib, module (+10 more)

### Community 10 - "Backend: Application Config"
Cohesion: 0.12
Nodes (15): name, password, url, username, ddl-auto, default_batch_fetch_size, format_sql, hibernate (+7 more)

### Community 11 - "Backend: Docker Postgres"
Cohesion: 0.15
Nodes (12): POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, container_name, environment, image, ports, volumes (+4 more)

### Community 17 - "AbilityControllerTest"
Cohesion: 0.14
Nodes (4): AbilityControllerTest, FindAll, FindById, FindPokemon

### Community 18 - "org.springframework.data.jpa.repository.JpaRepository"
Cohesion: 0.07
Nodes (10): ItemSummaryDto, LearnsetEntryDto, MoveDto, MoveLevelByGenerationDto, Move, GameRepository, ItemGenerationAvailabilityRepository, MoveRepository (+2 more)

### Community 20 - "org.junit.jupiter.api.Test"
Cohesion: 0.09
Nodes (8): PageDto, FindById, FindMoves, FindAll, FindAll, FindById, FindPage, FindById

### Community 21 - "org.junit.jupiter.api.Nested"
Cohesion: 0.09
Nodes (8): FindAll, FindPokemon, FindGames, FindGames, FindLearnset, FindMoveLevels, FindAll, FindPokemon

## Knowledge Gaps
- **1 isolated node(s):** `com.pokemonlb:pokemonlb`
  These have ≤1 connection - possible missing edges. (Counts symbols only; 123 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **13 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NotFoundException` connect `list` to `PokemonControllerTest`, `PokemonControllerTest.java`, `Backend: Exception Handling`, `org.junit.jupiter.api.Test`, `org.junit.jupiter.api.Nested`?**
  _High betweenness centrality (0.036) - this node is a cross-community bridge._
- **What connects `com.pokemonlb:pokemonlb` to the rest of the system?**
  _1 weakly-connected nodes found - possible missing edges._
- **Should `PokemonControllerTest` be split into smaller, more focused modules?**
  _Cohesion score 0.14705882352941177 - nodes in this community are weakly interconnected._
- **Why does `Pokemon` connect `Pokemon` to `Backend: JPA Entities`, `PokemonControllerTest.java`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Pokemon` be split into smaller, more focused modules?**
  _Cohesion score 0.10098522167487685 - nodes in this community are weakly interconnected._
- **Should `Backend: JPA Entities` be split into smaller, more focused modules?**
  _Cohesion score 0.08982456140350877 - nodes in this community are weakly interconnected._
- **Should `Frontend: Config and Dependencies` be split into smaller, more focused modules?**
  _Cohesion score 0.07954545454545454 - nodes in this community are weakly interconnected._