# Graph Report - backend  (2026-11-19)

## Corpus Check
- 86 files · ~3,305,724 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 2 file(s) not represented in the graph (top: .properties 1, .cmd 1)

## Summary
- 532 nodes · 1334 edges · 27 communities (20 shown, 7 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 84 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `5c64879e`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Pokémon API
- org.springframework.web.bind.annotation.GetMapping
- JPA Entity Mappings
- Data Injection Runner
- Move
- GameControllerTest
- org.junit.jupiter.api.Test
- org.springframework.data.jpa.repository.JpaRepository
- FindEvolutions
- Application Config (YAML)
- Docker Postgres Setup
- ItemSummaryDto
- Global Exception Handler
- App Entry Point
- Maven Project Coordinates
- org.junit.jupiter.api.Nested
- PokemonControllerTest
- com.pokemonlb.model.entity.Pokemon
- pokemonLB
- Games API
- Moves API
- API
- Types API
- Items API

## God Nodes (most connected - your core abstractions)
1. `PokemonService` - 20 edges
2. `GameDto` - 20 edges
3. `PokemonMove` - 17 edges
4. `MoveGameAvailability` - 16 edges
5. `NotFoundException` - 16 edges
6. `ItemSummaryDto` - 16 edges
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

## Communities (27 total, 7 thin omitted)

### Community 0 - "Pokémon API"
Cohesion: 0.12
Nodes (17): A Pokémon that doesn't evolve: Tauros, All Pokémon, Branching: Eevee, Errors, Evolution family, Games a Pokémon is in, Generation 4 Pokémon in Legends: Arceus, Get one Pokémon (+9 more)

### Community 1 - "org.springframework.web.bind.annotation.GetMapping"
Cohesion: 0.06
Nodes (19): API documentation, Before you start, Conventions, Errors, ApiUrls, Games, Items, Moves (+11 more)

### Community 2 - "JPA Entity Mappings"
Cohesion: 0.13
Nodes (14): Application layers (`com.pokemonlb`), Game, ItemGameAvailability, Key, ItemGenerationAvailability, Key, MoveLearnMethod, Pokemon (+6 more)

### Community 3 - "Data Injection Runner"
Cohesion: 0.06
Nodes (11): Architecture, Commands, Data model decisions that aren't obvious from one file, Project, Schema and data are deliberately separate, Tests, Tests, Config (+3 more)

### Community 4 - "Move"
Cohesion: 0.19
Nodes (4): LearnsetEntryDto, MoveDto, Move, MoveRepository

### Community 5 - "GameControllerTest"
Cohesion: 0.19
Nodes (6): FindMoves, GameControllerTest, ItemControllerTest, MoveControllerTest, FindAll, TypeControllerTest

### Community 6 - "org.junit.jupiter.api.Test"
Cohesion: 0.10
Nodes (7): FindById, FindAll, FindById, FindAll, FindById, FindAll, FindById

### Community 7 - "org.springframework.data.jpa.repository.JpaRepository"
Cohesion: 0.09
Nodes (12): NotFoundException, GameDto, ItemDetailDto, MoveSummaryDto, Key, MoveGameAvailability, GameRepository, ItemGameAvailabilityRepository (+4 more)

### Community 10 - "Application Config (YAML)"
Cohesion: 0.12
Nodes (15): name, password, url, username, ddl-auto, default_batch_fetch_size, format_sql, hibernate (+7 more)

### Community 11 - "Docker Postgres Setup"
Cohesion: 0.15
Nodes (12): POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, container_name, environment, image, ports, volumes (+4 more)

### Community 12 - "ItemSummaryDto"
Cohesion: 0.20
Nodes (4): ItemSummaryDto, MoveLevelByGenerationDto, ItemGenerationAvailabilityRepository, PokemonMoveRepository

### Community 17 - "org.junit.jupiter.api.Nested"
Cohesion: 0.13
Nodes (5): FindAll, FindItems, FindPokemon, FindGames, FindPokemon

### Community 18 - "PokemonControllerTest"
Cohesion: 0.12
Nodes (5): FindById, FindGames, FindLearnset, FindMoveLevels, PokemonControllerTest

### Community 19 - "com.pokemonlb.model.entity.Pokemon"
Cohesion: 0.07
Nodes (10): EvolutionDto, PokemonRefDto, PokemonSummaryDto, TypeDto, PokemonEvolution, PokemonEvolutionRepository, PokemonRepository, PokemonStatsRepository (+2 more)

### Community 20 - "pokemonLB"
Cohesion: 0.22
Nodes (9): 1. Start a database, 2. Fill the database (once), 3. Run the API, Credits, Data notes, Getting started, pokemonLB, Prerequisites (+1 more)

### Community 21 - "Games API"
Cohesion: 0.25
Nodes (8): Errors, Game ids, Games API, Get one game, Items in a game, List games, Moves in a game, Pokémon in a game

### Community 23 - "Moves API"
Cohesion: 0.29
Nodes (7): Errors, Games a move is in, Get one move, List moves, Move fields, Moves API, Only Electric moves

### Community 24 - "API"
Cohesion: 0.29
Nodes (7): API, Examples, Games — `/api/v1/games`, Items — `/api/v1/items`, Moves — `/api/v1/moves`, Pokémon — `/api/v1/pokemon`, Types — `/api/v1/types`

### Community 25 - "Types API"
Cohesion: 0.33
Nodes (6): Errors, Get one type, List types, Pokémon of a type, Type ids, Types API

### Community 26 - "Items API"
Cohesion: 0.40
Nodes (5): Errors, Get one item, Items API, List items, Only Generation 9

## Knowledge Gaps
- **1 isolated node(s):** `com.pokemonlb:pokemonlb`
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 120 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **7 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PokemonController` connect `org.springframework.web.bind.annotation.GetMapping` to `Pokémon API`, `PokemonControllerTest`?**
  _High betweenness centrality (0.092) - this node is a cross-community bridge._
- **What connects `com.pokemonlb:pokemonlb` to the rest of the system?**
  _1 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Pokémon API` be split into smaller, more focused modules?**
  _Cohesion score 0.11764705882352941 - nodes in this community are weakly interconnected._
- **Why does `Pokémon API` connect `Pokémon API` to `org.springframework.web.bind.annotation.GetMapping`, `README.md`?**
  _High betweenness centrality (0.073) - this node is a cross-community bridge._
- **Should `org.springframework.web.bind.annotation.GetMapping` be split into smaller, more focused modules?**
  _Cohesion score 0.06293706293706294 - nodes in this community are weakly interconnected._
- **Why does `pokemonLB` connect `pokemonLB` to `API`, `Data Injection Runner`, `README.md`?**
  _High betweenness centrality (0.063) - this node is a cross-community bridge._
- **Should `JPA Entity Mappings` be split into smaller, more focused modules?**
  _Cohesion score 0.12996632996632998 - nodes in this community are weakly interconnected._