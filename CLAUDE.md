# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Read-only REST API for Pokémon data (species, stats, games, moves/learnsets, items). Spring Boot 4.1 on Java 25, Maven, PostgreSQL 17, Flyway, Lombok. The whole Maven project lives in `backend/`; the repo root only holds git/IDE files and this file.

## Commands

Run everything from `backend/`. Java 25 is required — the `java` on PATH on the dev machine is 21, so set `JAVA_HOME` (JDK at `C:\Program Files\Java\jdk-25.0.5.1`) or pick JDK 25 as the IntelliJ project SDK.

```bash
./mvnw clean package -DskipTests                 # build (use clean after renaming/deleting migrations: stale copies in target/ break Flyway)
./mvnw spring-boot:run                           # run the API (port 8080)
./mvnw test                                      # all tests
./mvnw test -Dtest=PokemonControllerTest         # one test class
./mvnw test -Dtest='PokemonControllerTest$FindById#returnsPokemonWithStats'   # one test (nested classes use $)

# Fill the database (separate entry point, see "Data injection")
./mvnw spring-boot:run -Dspring-boot.run.main-class=com.pokemonlb.datainject.DbDataInject
./mvnw spring-boot:run -Dspring-boot.run.main-class=com.pokemonlb.datainject.DbDataInject -Dspring-boot.run.arguments=--force
```

Database connection comes from `application.yml`, overridable with `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` (defaults: `jdbc:postgresql://localhost:5432/pokemonlb`, `postgres` / `1234`). Locally Postgres runs as the Windows service `postgresql-x64-17`; `docker-compose.yml` is an alternative with the same defaults (both use port 5432). On the dev machine the `PEMHTTPD-x64` service (Apache from the Postgres install) may hold port 8080 — stop it or run with `--server.port=8081`.

## Architecture

### Schema and data are deliberately separate

- **Schema**: Flyway migrations in `src/main/resources/db/migration` (V1–V6), schema-only, applied on every app start. `spring.jpa.hibernate.ddl-auto` is `validate`, so any entity change needs a new migration (`V7__...`); never edit an applied migration.
- **Data**: `src/main/java/com/pokemonlb/datainject/` holds `DbDataInject` (its own `main`), `SqlScriptInjector`, and `sql/00_clear.sql`…`09_evolutions.sql`. Starting the app never inserts data. A new data table needs its own numbered SQL file and an entry in `00_clear.sql`; existing databases only pick it up with `--force`, since the injector skips once `pokemon` has rows.
  - Runs all `sql/*.sql` in file-name order in one transaction (all-or-nothing). Skips entirely if `pokemon` already has rows; `--force` runs `00_clear.sql` (TRUNCATE … CASCADE) and re-injects.
  - The SQL folder is read from disk via a path relative to the working directory, so the injector must run with `backend/` as the working directory. The SQL files are not on the classpath or in the jar.
  - `DbDataInject.Config` is `@EnableAutoConfiguration` but intentionally **not** `@Configuration`: the package sits inside the app's component scan, and nothing in it may become a Spring component or the app would start injecting.
  - `pom.xml` sets `<start-class>` because there are two `main` methods.
- The SQL data was generated from PokeAPI's CSV exports (github.com/PokeAPI/pokeapi, `data/v2/csv`); the generator scripts are not in the repo. `06_pokemon_moves.sql` is ~15 MB / ~600k rows and takes ~20 s.

### Data model decisions that aren't obvious from one file

- `pokemon.id` is the National Pokédex number (1–1025, base species only; no alternate forms). Stats are per species' default form.
- `game.id` = PokeAPI **version group** id. Paired versions share a row (e.g. "Sword / Shield"), so version exclusives are not modeled. DLC are their own rows in `game`. `release_order` is the chronological sort key; ids are not chronological.
- `pokemon_game`: species is "in" a game if it's in that game's regional Pokédex(es).
- `pokemon_move` is per **game**, not per generation, because learn levels differ between games of the same generation. `level` is only meaningful for learn method 1 ("Level up"); `0` = learned on evolution, and it is `0` for every other method. The view `pokemon_move_generation` aggregates per generation (`min_level`, `max_level`, `levels`).
- `pokemon_type` holds each species' **current** typing with `slot` 1 (primary) / 2 (secondary); past typings (e.g. Clefairy was Normal before Gen 6) are not modeled. `type` has the 18 types only. `move.type` is still a plain string, not a foreign key to `type`.
- `pokemon_evolution`: one row per step (`from_pokemon_id` → `to_pokemon_id`), id = PokeAPI evolution id, built from PokeAPI's `is_default` rows only (the **current** method; older per-game methods dropped) and skipping steps whose result is a special form. Every species has at most one pre-evolution. Common conditions are columns (`min_level`, `item`, `held_item`, `known_move`, `known_move_type`, `min_happiness`, `time_of_day`, `gender`); rare ones are generated English text in `other_conditions`. `PokemonService.findEvolutions` walks up via `findByTo_Id` to the first stage, then down stage by stage to return the whole family.
- `move.name` is not unique (Z-Moves have physical and special variants).
- `move_game_availability`, `item_game_availability`, `item_generation_availability` are standalone, denormalized tables with **no foreign keys** (names stored inline) — intended to stay unconnected. DLC is folded into its base game here. Per-game item data only exists up to Sword/Shield (later games only list TMs); `item_generation_availability` is the complete source.
- `pokemon.image_url` points at PokeAPI's official-artwork PNGs on GitHub (hotlinked).

### Application layers (`com.pokemonlb`)

`controller` → `service` → `repository` → `model.entity`, with `model.dto` records as the only API response types.

- `config/ApiUrls`: all URL constants, one nested class per controller (`ApiUrls.Pokemon.BASE`, `ApiUrls.Pokemon.MOVE_LEVELS`, …). Controllers use these instead of string literals. Base path `/api/v1`.
- Services are `@Transactional(readOnly = true)` and do all entity→DTO mapping (`open-in-view` is false, so lazy associations must be touched inside the service). DTOs map via static `from(entity)` factories.
- `hibernate.default_batch_fetch_size: 100` in `application.yml` keeps lazy collections (e.g. `Pokemon.types` on the 1025-row list) from turning into one query per row; listing all Pokémon takes ~14 queries.
- Some repository queries construct DTOs directly in JPQL (`select new com.pokemonlb.model.dto.…`) — renaming/moving those DTOs requires updating the query strings.
- Repositories use `@Query` with named parameters or derived queries only; keep it that way (no string-concatenated JPQL/SQL).
- Missing resources: services throw `exception.NotFoundException`, which `GlobalExceptionHandler` turns into a 404 `ProblemDetail`.
- `PokemonMove` is a full entity with an `@EmbeddedId` (`PokemonMove.Id`) and `@MapsId` relations, because the link carries method and level; `PokemonType` works the same way for the slot. `PokemonStats` shares the `Pokemon` id via `@MapsId`.

### Tests

- `controller/*ControllerTest`: `@WebMvcTest` slices with services replaced by `@MockitoBean`; no database needed.
- `service/PokemonServiceEvolutionTest`: plain unit test of the evolution-family walk, with mocked repositories and mocked entities (entities have no public constructors). Build mock lists before passing them to `given(...)`, never inline — Mockito rejects stubbing inside stubbing. Spring Boot 4 package for the slice annotation: `org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest`.
- `PokemonlbApplicationTests.contextLoads` starts the full app and needs the local Postgres running.
