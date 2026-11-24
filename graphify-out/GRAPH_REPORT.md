# Graph Report - pokemonLB  (2026-11-24)

## Corpus Check
- 108 files · ~14,034 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 6 file(s) not represented in the graph (top: (none) 4, .example 1, .css 1)

## Summary
- 699 nodes · 1800 edges · 31 communities (15 shown, 16 thin omitted)
- Extraction: 95% EXTRACTED · 5% INFERRED · 0% AMBIGUOUS · INFERRED: 84 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a0a06fad`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- org.junit.jupiter.api.Test
- PokemonService.java
- FindById
- Backend: JPA Entities
- Frontend: Config and Dependencies
- Frontend: Homepage, Services and Hooks
- list
- Backend: Data Injection Runner
- Frontend: TypeScript Config
- PokemonAbility
- Backend: Application Config
- Backend: Docker Postgres
- PokemonControllerTest.java
- FindLearnset
- Backend: Exception Handling
- Backend: App Entry Point
- Backend: Maven Coordinates
- FindAll
- list
- FindAll
- FindPage
- org.junit.jupiter.api.Nested
- FindAll
- PokemonSpecifications.java
- FindById
- FindPokemon
- FindMoves
- FindGames
- FindEvolutions
- FindMoveLevels
- PokemonlbApplicationTests.java

## God Nodes (most connected - your core abstractions)
1. `Pokemon` - 25 edges
2. `PokemonService` - 20 edges
3. `GameDto` - 18 edges
4. `SecurityConfig` - 17 edges
5. `PokemonAbility` - 17 edges
6. `PokemonControllerTest` - 16 edges
7. `MoveGameAvailability` - 16 edges
8. `PokemonMove` - 16 edges
9. `GameService` - 16 edges
10. `compilerOptions` - 16 edges

## Surprising Connections (you probably didn't know these)
- `PokemonControllerTest` --references--> `SecurityConfig`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/PokemonControllerTest.java → backend/src/main/java/com/pokemonlb/config/SecurityConfig.java
- `AbilityControllerTest` --references--> `AbilityController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/AbilityControllerTest.java → backend/src/main/java/com/pokemonlb/controller/AbilityController.java
- `GameControllerTest` --references--> `GameController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/GameControllerTest.java → backend/src/main/java/com/pokemonlb/controller/GameController.java
- `ItemControllerTest` --references--> `ItemController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/ItemControllerTest.java → backend/src/main/java/com/pokemonlb/controller/ItemController.java
- `MoveControllerTest` --references--> `MoveController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/MoveControllerTest.java → backend/src/main/java/com/pokemonlb/controller/MoveController.java

## Import Cycles
- None detected.

## Communities (31 total, 16 thin omitted)

### Community 0 - "org.junit.jupiter.api.Test"
Cohesion: 0.15
Nodes (5): FindById, FindById, FindById, FindAbilities, FindById

### Community 1 - "PokemonService.java"
Cohesion: 0.08
Nodes (5): PokemonController, PokemonEvolutionRepository, PokemonService, PokemonControllerTest, PokemonServiceEvolutionTest

### Community 2 - "FindById"
Cohesion: 0.24
Nodes (3): PokemonDetailDto, PokemonStatsDto, FindById

### Community 3 - "Backend: JPA Entities"
Cohesion: 0.09
Nodes (20): EvolutionDto, PokemonRefDto, Game, ItemGameAvailability, Key, ItemGenerationAvailability, Key, Key (+12 more)

### Community 4 - "Frontend: Config and Dependencies"
Cohesion: 0.08
Nodes (31): eslintConfig, dependencies, next@16.4.6, react@19.3.0, react-dom@19.3.0, devDependencies, eslint@^9, eslint-config-next@16.4.6 (+23 more)

### Community 5 - "Frontend: Homepage, Services and Hooks"
Cohesion: 0.07
Nodes (44): nextConfig, GET(), toInteger(), lilita, metadata, nunito, ApiUnavailable(), Catalog() (+36 more)

### Community 6 - "list"
Cohesion: 0.05
Nodes (26): Abilities, ApiUrls, Games, Items, Moves, Pokemon, Types, GameController (+18 more)

### Community 7 - "Backend: Data Injection Runner"
Cohesion: 0.06
Nodes (4): ApiKeyFilter, Config, DbDataInject, SqlScriptInjector

### Community 8 - "Frontend: TypeScript Config"
Cohesion: 0.11
Nodes (18): compilerOptions, allowJs, esModuleInterop, incremental, isolatedModules, jsx, lib, module (+10 more)

### Community 9 - "PokemonAbility"
Cohesion: 0.12
Nodes (9): AbilityController, AbilityDto, AbilityPokemonDto, PokemonAbilityDto, Ability, PokemonAbility, AbilityRepository, PokemonAbilityRepository (+1 more)

### Community 10 - "Backend: Application Config"
Cohesion: 0.11
Nodes (17): name, password, url, username, ddl-auto, default_batch_fetch_size, format_sql, hibernate (+9 more)

### Community 11 - "Backend: Docker Postgres"
Cohesion: 0.15
Nodes (12): POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, container_name, environment, image, ports, volumes (+4 more)

### Community 12 - "PokemonControllerTest.java"
Cohesion: 0.09
Nodes (7): SecurityConfig, SecurityConfigTest, AbilityControllerTest, GameControllerTest, ItemControllerTest, MoveControllerTest, TypeControllerTest

### Community 18 - "list"
Cohesion: 0.10
Nodes (7): MoveLevelByGenerationDto, Move, GameRepository, MoveGameAvailabilityRepository, MoveRepository, PokemonMoveRepository, PokemonStatsRepository

### Community 21 - "org.junit.jupiter.api.Nested"
Cohesion: 0.12
Nodes (6): FindAll, FindItems, FindPokemon, FindGames, FindAll, FindPokemon

## Knowledge Gaps
- **1 isolated node(s):** `com.pokemonlb:pokemonlb`
  These have ≤1 connection - possible missing edges. (Counts symbols only; 136 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **16 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SecurityConfig` connect `PokemonControllerTest.java` to `PokemonService.java`?**
  _High betweenness centrality (0.036) - this node is a cross-community bridge._
- **What connects `com.pokemonlb:pokemonlb` to the rest of the system?**
  _1 weakly-connected nodes found - possible missing edges._
- **Should `org.junit.jupiter.api.Test` be split into smaller, more focused modules?**
  _Cohesion score 0.14736842105263157 - nodes in this community are weakly interconnected._
- **Why does `Pokemon` connect `Backend: JPA Entities` to `PokemonAbility`, `FindById`, `list`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `PokemonService.java` be split into smaller, more focused modules?**
  _Cohesion score 0.08082706766917293 - nodes in this community are weakly interconnected._
- **Should `Backend: JPA Entities` be split into smaller, more focused modules?**
  _Cohesion score 0.08691308691308691 - nodes in this community are weakly interconnected._
- **Should `Frontend: Config and Dependencies` be split into smaller, more focused modules?**
  _Cohesion score 0.07954545454545454 - nodes in this community are weakly interconnected._