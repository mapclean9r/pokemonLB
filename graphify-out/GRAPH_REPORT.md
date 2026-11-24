# Graph Report - pokemonLB  (2026-11-24)

## Corpus Check
- 85 files · ~10,233 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 6 file(s) not represented in the graph (top: (none) 4, .example 1, .css 1)

## Summary
- 524 nodes · 1425 edges · 17 communities (12 shown, 5 thin omitted)
- Extraction: 96% EXTRACTED · 4% INFERRED · 0% AMBIGUOUS · INFERRED: 57 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `3265ebae`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Backend: Controller Tests and DTOs
- Backend: API URLs and Controllers
- Backend: Availability and Repositories
- Backend: JPA Entities
- Frontend: Config and Dependencies
- Frontend: Homepage, Services and Hooks
- Backend: Pokemon and Evolutions
- Backend: Data Injection Runner
- Frontend: TypeScript Config
- Backend: Item and Level Queries
- Backend: Application Config
- Backend: Docker Postgres
- Backend: Learnsets and Moves
- Backend: Pokemon Details and Stats
- Backend: Exception Handling
- Backend: App Entry Point
- Backend: Maven Coordinates

## God Nodes (most connected - your core abstractions)
1. `NotFoundException` - 38 edges
2. `Pokemon` - 33 edges
3. `GameDto` - 28 edges
4. `PokemonService` - 22 edges
5. `PokemonSummaryDto` - 20 edges
6. `GameService` - 18 edges
7. `PokemonEvolution` - 18 edges
8. `PokemonRepository` - 17 edges
9. `ItemSummaryDto` - 17 edges
10. `MoveGameAvailability` - 16 edges

## Surprising Connections (you probably didn't know these)
- `GameControllerTest` --references--> `GameController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/GameControllerTest.java → backend/src/main/java/com/pokemonlb/controller/GameController.java
- `GameControllerTest` --references--> `GameDto`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/GameControllerTest.java → backend/src/main/java/com/pokemonlb/model/dto/GameDto.java
- `GameControllerTest` --references--> `GameService`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/GameControllerTest.java → backend/src/main/java/com/pokemonlb/service/GameService.java
- `ItemControllerTest` --references--> `ItemController`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/ItemControllerTest.java → backend/src/main/java/com/pokemonlb/controller/ItemController.java
- `ItemControllerTest` --references--> `ItemSummaryDto`  [EXTRACTED]
  backend/src/test/java/com/pokemonlb/controller/ItemControllerTest.java → backend/src/main/java/com/pokemonlb/model/dto/ItemSummaryDto.java

## Import Cycles
- None detected.

## Communities (17 total, 5 thin omitted)

### Community 0 - "Backend: Controller Tests and DTOs"
Cohesion: 0.05
Nodes (25): NotFoundException, FindAll, FindById, FindItems, FindMoves, FindPokemon, GameControllerTest, FindAll (+17 more)

### Community 1 - "Backend: API URLs and Controllers"
Cohesion: 0.08
Nodes (16): ApiUrls, Games, Items, Moves, Pokemon, Types, GameController, ItemController (+8 more)

### Community 2 - "Backend: Availability and Repositories"
Cohesion: 0.09
Nodes (15): MoveSummaryDto, Key, MoveGameAvailability, GameRepository, ItemGameAvailabilityRepository, MoveGameAvailabilityRepository, MoveRepository, PokemonMoveRepository (+7 more)

### Community 3 - "Backend: JPA Entities"
Cohesion: 0.14
Nodes (13): Game, ItemGameAvailability, Key, ItemGenerationAvailability, Key, Move, MoveLearnMethod, Id (+5 more)

### Community 4 - "Frontend: Config and Dependencies"
Cohesion: 0.06
Nodes (37): eslintConfig, nextConfig, dependencies, next@16.4.6, react@19.3.0, react-dom@19.3.0, devDependencies, eslint@^9 (+29 more)

### Community 5 - "Frontend: Homepage, Services and Hooks"
Cohesion: 0.12
Nodes (28): ApiUnavailable(), Catalog(), HomePage(), loadCatalog(), CatalogSkeleton(), Hero(), FilterChip(), GenerationButton() (+20 more)

### Community 6 - "Backend: Pokemon and Evolutions"
Cohesion: 0.10
Nodes (7): EvolutionDto, PokemonRefDto, Pokemon, PokemonEvolution, PokemonEvolutionRepository, FindEvolutions, PokemonServiceEvolutionTest

### Community 7 - "Backend: Data Injection Runner"
Cohesion: 0.09
Nodes (3): Config, DbDataInject, SqlScriptInjector

### Community 8 - "Frontend: TypeScript Config"
Cohesion: 0.11
Nodes (18): compilerOptions, allowJs, esModuleInterop, incremental, isolatedModules, jsx, lib, module (+10 more)

### Community 9 - "Backend: Item and Level Queries"
Cohesion: 0.17
Nodes (3): ItemSummaryDto, MoveLevelByGenerationDto, ItemGenerationAvailabilityRepository

### Community 10 - "Backend: Application Config"
Cohesion: 0.12
Nodes (15): name, password, url, username, ddl-auto, default_batch_fetch_size, format_sql, hibernate (+7 more)

### Community 11 - "Backend: Docker Postgres"
Cohesion: 0.15
Nodes (12): POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, container_name, environment, image, ports, volumes (+4 more)

## Knowledge Gaps
- **1 isolated node(s):** `com.pokemonlb:pokemonlb`
  These have ≤1 connection - possible missing edges. (Counts symbols only; 102 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **5 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `NotFoundException` connect `Backend: Controller Tests and DTOs` to `Backend: API URLs and Controllers`, `Backend: Availability and Repositories`, `Backend: Pokemon and Evolutions`, `Backend: Item and Level Queries`, `Backend: Learnsets and Moves`, `Backend: Exception Handling`?**
  _High betweenness centrality (0.067) - this node is a cross-community bridge._
- **What connects `com.pokemonlb:pokemonlb` to the rest of the system?**
  _1 weakly-connected nodes found - possible missing edges._
- **Should `Backend: Controller Tests and DTOs` be split into smaller, more focused modules?**
  _Cohesion score 0.05111683848797251 - nodes in this community are weakly interconnected._
- **Why does `Pokemon` connect `Backend: Pokemon and Evolutions` to `Backend: Controller Tests and DTOs`, `Backend: Availability and Repositories`, `Backend: JPA Entities`, `Backend: Pokemon Details and Stats`?**
  _High betweenness centrality (0.042) - this node is a cross-community bridge._
- **Should `Backend: API URLs and Controllers` be split into smaller, more focused modules?**
  _Cohesion score 0.07868852459016394 - nodes in this community are weakly interconnected._
- **Should `Backend: Availability and Repositories` be split into smaller, more focused modules?**
  _Cohesion score 0.08583959899749373 - nodes in this community are weakly interconnected._
- **Should `Backend: JPA Entities` be split into smaller, more focused modules?**
  _Cohesion score 0.14027149321266968 - nodes in this community are weakly interconnected._