# Graph Report - pokemonLB  (2026-11-24)

## Corpus Check
- 105 files · ~13,364 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 6 file(s) not represented in the graph (top: (none) 4, .example 1, .css 1)

## Summary
- 648 nodes · 1716 edges · 31 communities (20 shown, 11 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 75 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `91d39aa7`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- org.junit.jupiter.api.Nested
- PokemonService.java
- MoveService
- Backend: JPA Entities
- Frontend: Config and Dependencies
- Frontend: Homepage, Services and Hooks
- PokemonController.java
- Backend: Data Injection Runner
- Frontend: TypeScript Config
- org.springframework.web.bind.annotation.GetMapping
- Backend: Application Config
- Backend: Docker Postgres
- PokemonControllerTest.java
- MoveGameAvailability
- Backend: Exception Handling
- Backend: App Entry Point
- Backend: Maven Coordinates
- AbilityControllerTest
- org.springframework.data.jpa.repository.JpaRepository
- lombok.RequiredArgsConstructor
- org.junit.jupiter.api.Test
- PokemonControllerTest
- layout.tsx
- PokemonSpecifications.java
- TypeDto
- MoveControllerTest
- GameDto
- ItemSummaryDto
- FindEvolutions
- ItemDetailDto
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
- `ItemControllerTest` --references--> `ItemController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/ItemControllerTest.java → backend/src/main/java/com/pokemonlb/controller/ItemController.java

## Import Cycles
- None detected.

## Communities (31 total, 11 thin omitted)

### Community 0 - "org.junit.jupiter.api.Nested"
Cohesion: 0.12
Nodes (8): NotFoundException, FindItems, FindMoves, FindPokemon, FindAll, FindById, FindPokemon, TypeControllerTest

### Community 1 - "PokemonService.java"
Cohesion: 0.07
Nodes (5): PokemonDetailDto, PokemonStatsDto, PokemonEvolutionRepository, PokemonService, PokemonServiceEvolutionTest

### Community 3 - "Backend: JPA Entities"
Cohesion: 0.09
Nodes (21): EvolutionDto, PokemonAbilityDto, PokemonRefDto, Ability, Game, ItemGameAvailability, Key, ItemGenerationAvailability (+13 more)

### Community 4 - "Frontend: Config and Dependencies"
Cohesion: 0.08
Nodes (31): eslintConfig, dependencies, next@16.4.6, react@19.3.0, react-dom@19.3.0, devDependencies, eslint@^9, eslint-config-next@16.4.6 (+23 more)

### Community 5 - "Frontend: Homepage, Services and Hooks"
Cohesion: 0.08
Nodes (41): nextConfig, GET(), toInteger(), ApiUnavailable(), Catalog(), HomePage(), loadCatalog(), CatalogSkeleton() (+33 more)

### Community 6 - "PokemonController.java"
Cohesion: 0.12
Nodes (10): Abilities, ApiUrls, Games, Items, Moves, Pokemon, Types, ItemController (+2 more)

### Community 7 - "Backend: Data Injection Runner"
Cohesion: 0.09
Nodes (3): Config, DbDataInject, SqlScriptInjector

### Community 8 - "Frontend: TypeScript Config"
Cohesion: 0.11
Nodes (18): compilerOptions, allowJs, esModuleInterop, incremental, isolatedModules, jsx, lib, module (+10 more)

### Community 9 - "org.springframework.web.bind.annotation.GetMapping"
Cohesion: 0.22
Nodes (4): AbilityController, AbilityDto, AbilityPokemonDto, PokemonSummaryDto

### Community 10 - "Backend: Application Config"
Cohesion: 0.12
Nodes (15): name, password, url, username, ddl-auto, default_batch_fetch_size, format_sql, hibernate (+7 more)

### Community 11 - "Backend: Docker Postgres"
Cohesion: 0.15
Nodes (12): POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, container_name, environment, image, ports, volumes (+4 more)

### Community 13 - "MoveGameAvailability"
Cohesion: 0.21
Nodes (4): MoveSummaryDto, Key, MoveGameAvailability, MoveGameAvailabilityRepository

### Community 17 - "AbilityControllerTest"
Cohesion: 0.14
Nodes (4): AbilityControllerTest, FindAll, FindById, FindPokemon

### Community 18 - "org.springframework.data.jpa.repository.JpaRepository"
Cohesion: 0.07
Nodes (10): LearnsetEntryDto, MoveDto, MoveLevelByGenerationDto, Move, AbilityRepository, GameRepository, MoveRepository, PokemonAbilityRepository (+2 more)

### Community 19 - "lombok.RequiredArgsConstructor"
Cohesion: 0.37
Nodes (4): ItemGameAvailabilityRepository, AbilityService, ItemService, TypeService

### Community 20 - "org.junit.jupiter.api.Test"
Cohesion: 0.11
Nodes (6): PageDto, FindById, FindAll, FindById, FindById, FindPage

### Community 21 - "PokemonControllerTest"
Cohesion: 0.14
Nodes (5): FindAbilities, FindGames, FindLearnset, FindMoveLevels, PokemonControllerTest

### Community 22 - "layout.tsx"
Cohesion: 0.33
Nodes (3): lilita, metadata, nunito

### Community 25 - "MoveControllerTest"
Cohesion: 0.17
Nodes (4): FindAll, FindById, FindGames, MoveControllerTest

### Community 26 - "GameDto"
Cohesion: 0.23
Nodes (5): GameController, GameDto, GameService, FindAll, GameControllerTest

## Knowledge Gaps
- **1 isolated node(s):** `com.pokemonlb:pokemonlb`
  These have ≤1 connection - possible missing edges. (Counts symbols only; 119 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **11 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NotFoundException` connect `org.junit.jupiter.api.Nested` to `MoveService`, `org.springframework.web.bind.annotation.GetMapping`, `PokemonControllerTest.java`, `Backend: Exception Handling`, `org.springframework.data.jpa.repository.JpaRepository`, `lombok.RequiredArgsConstructor`, `org.junit.jupiter.api.Test`, `TypeDto`, `MoveControllerTest`, `GameDto`, `ItemDetailDto`?**
  _High betweenness centrality (0.037) - this node is a cross-community bridge._
- **What connects `com.pokemonlb:pokemonlb` to the rest of the system?**
  _1 weakly-connected nodes found - possible missing edges._
- **Should `org.junit.jupiter.api.Nested` be split into smaller, more focused modules?**
  _Cohesion score 0.12380952380952381 - nodes in this community are weakly interconnected._
- **Why does `Pokemon` connect `Backend: JPA Entities` to `PokemonService.java`, `PokemonControllerTest.java`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `PokemonService.java` be split into smaller, more focused modules?**
  _Cohesion score 0.07205513784461152 - nodes in this community are weakly interconnected._
- **Should `Backend: JPA Entities` be split into smaller, more focused modules?**
  _Cohesion score 0.08512658227848101 - nodes in this community are weakly interconnected._
- **Should `Frontend: Config and Dependencies` be split into smaller, more focused modules?**
  _Cohesion score 0.07954545454545454 - nodes in this community are weakly interconnected._