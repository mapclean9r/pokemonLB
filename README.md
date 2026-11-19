# pokemonLB

A read-only REST API for Pokémon data: every species (1–1025) with types, base stats, artwork and evolutions, the games they appear in, full learnsets per game (move, method and level), and which moves and items exist in which games.

Built with Spring Boot 4.1, Java 25, PostgreSQL 17, Flyway and Lombok.

## Prerequisites

- **JDK 25.** Check with `java -version`; if it shows an older version, point `JAVA_HOME` at a JDK 25 install.
- **PostgreSQL 17**, either installed locally or via Docker (`backend/docker-compose.yml`).
- No Maven install needed — the project ships with the Maven wrapper (`mvnw`).

## Getting started

All commands run from the `backend/` folder.

```bash
cd backend
```

### 1. Start a database

With Docker:

```bash
docker compose up -d
```

Or with a local PostgreSQL, create the database:

```sql
CREATE DATABASE pokemonlb;
```

The app connects to `jdbc:postgresql://localhost:5432/pokemonlb` as `postgres` / `1234` by default. The database settings live in two files:

| File | What it sets |
|---|---|
| [`backend/src/main/resources/application.yml`](backend/src/main/resources/application.yml) | What the app (and the data injector) connects with: `spring.datasource.url`, `username` and `password` |
| [`backend/docker-compose.yml`](backend/docker-compose.yml) | The user, password and database the Docker container is created with: `POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DB` |

If you change the username or password, change it in both files so they match. Docker only applies `docker-compose.yml` when the database is first created, so after a change run `docker compose down -v` (this deletes the data) and start it again. With a local PostgreSQL, only `application.yml` matters — set it to your own login.

Each value in `application.yml` is written as `${VARIABLE:default}`, so you can also override it with an environment variable without editing the file:

| Variable | Default |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/pokemonlb` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | `1234` |

### 2. Fill the database (once)

Starting the app only creates empty tables. To load the data, run this file:

[`backend/src/main/java/com/pokemonlb/datainject/DbDataInject.java`](backend/src/main/java/com/pokemonlb/datainject/DbDataInject.java)

It creates the tables if needed, then inserts all data in one transaction (about 30 seconds — the learnsets are ~600,000 rows). If the database already has data it does nothing. To wipe and reload everything, add `--force` as a program argument in the run configuration.

### 3. Run the API

Run this file:

[`backend/src/main/java/com/pokemonlb/PokemonlbApplication.java`](backend/src/main/java/com/pokemonlb/PokemonlbApplication.java)

The API starts on <http://localhost:8080>. If that port is taken, add `--server.port=8081` as a program argument in the run configuration.

Try it: <http://localhost:8080/api/v1/pokemon/25>

## API

All endpoints are `GET` and return JSON. Unknown ids return `404` with a JSON error body; non-numeric ids return `400`.

**Full documentation with real request and response examples** for every endpoint is in [`docs/api/`](docs/api/README.md): [Pokémon](docs/api/pokemon.md) · [Games](docs/api/games.md) · [Moves](docs/api/moves.md) · [Items](docs/api/items.md) · [Types](docs/api/types.md). The tables below are a quick overview.

### Pokémon — `/api/v1/pokemon`

| Endpoint | Returns |
|---|---|
| `/api/v1/pokemon?generation=&gameId=` | All Pokémon with their types; both filters optional |
| `/api/v1/pokemon/{id}` | One Pokémon with types, base stats and image URL |
| `/api/v1/pokemon/{id}/games` | Games whose Pokédex includes it |
| `/api/v1/pokemon/{id}/moves?gameId=` | Its learnset in a game: move, learn method, level |
| `/api/v1/pokemon/{id}/moves/{moveId}/levels` | The level it learns a move at, per generation |
| `/api/v1/pokemon/{id}/evolutions` | Its whole evolution family: who evolves into who, and how (level, item, trade, ...) |

### Games — `/api/v1/games`

| Endpoint | Returns |
|---|---|
| `/api/v1/games` | All games in release order |
| `/api/v1/games/{id}` | One game |
| `/api/v1/games/{id}/pokemon` | Pokémon in the game's Pokédex |
| `/api/v1/games/{id}/moves` | Moves that exist in the game |
| `/api/v1/games/{id}/items` | Items in the game (see data notes) |

### Moves — `/api/v1/moves`

| Endpoint | Returns |
|---|---|
| `/api/v1/moves?type=` | All moves; optional type filter, e.g. `Electric` |
| `/api/v1/moves/{id}` | One move: type, category, power, accuracy, PP, priority |
| `/api/v1/moves/{id}/games` | Games the move exists in |

### Items — `/api/v1/items`

| Endpoint | Returns |
|---|---|
| `/api/v1/items?generation=` | All items; optional generation filter |
| `/api/v1/items/{id}` | One item with the generations and games it appears in |

### Types — `/api/v1/types`

| Endpoint | Returns |
|---|---|
| `/api/v1/types` | All 18 types, with the generation each was introduced |
| `/api/v1/types/{id}` | One type |
| `/api/v1/types/{id}/pokemon` | Pokémon with that type as primary or secondary type |

### Examples

`GET /api/v1/pokemon/6`:

```json
{
  "id": 6,
  "name": "Charizard",
  "generation": 1,
  "types": ["Fire", "Flying"],
  "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
  "stats": { "hp": 78, "attack": 84, "defense": 78, "specialAttack": 109, "specialDefense": 85, "speed": 100, "total": 534 }
}
```

`types` lists the primary type first.

`GET /api/v1/pokemon/5/evolutions` — Charmeleon's family, from the first stage down (shortened):

```json
[
  { "from": { "id": 4, "name": "Charmander" }, "to": { "id": 5, "name": "Charmeleon" }, "trigger": "Level up", "minLevel": 16 },
  { "from": { "id": 5, "name": "Charmeleon" }, "to": { "id": 6, "name": "Charizard" }, "trigger": "Level up", "minLevel": 36 }
]
```

Each step also has `item`, `heldItem`, `knownMove`, `knownMoveType`, `minHappiness`, `timeOfDay`, `gender` and `otherConditions`; only the ones that apply are filled in, the rest are `null`. Pikachu → Raichu, for example, is `"trigger": "Use item"` with `"item": "Thunder Stone"`. Pokémon that don't evolve return `[]`.

`GET /api/v1/pokemon/25/moves/85/levels` — when Pikachu learns Thunderbolt (shortened to two generations):

```json
[
  { "generation": 1, "minLevel": 26, "maxLevel": 26 },
  { "generation": 7, "minLevel": 21, "maxLevel": 42 }
]
```

`minLevel` and `maxLevel` differ when games in the same generation disagree (here: Sun/Moon vs. Let's Go).

## Tests

```bash
./mvnw test
./mvnw test -Dtest=PokemonControllerTest
```

The controller tests mock the services and need no database. `PokemonlbApplicationTests` starts the full app and needs PostgreSQL running.

## Project structure

```
backend/
├── docker-compose.yml
├── pom.xml
└── src/main/
    ├── java/com/pokemonlb/
    │   ├── config/        ApiUrls — every URL, grouped by controller
    │   ├── controller/    REST endpoints
    │   ├── service/       business logic, entity → DTO mapping
    │   ├── repository/    Spring Data JPA repositories
    │   ├── model/entity/  JPA entities (database tables)
    │   ├── model/dto/     API response records
    │   ├── exception/     404 handling
    │   └── datainject/    separate program that fills the database, plus its SQL files
    └── resources/
        ├── application.yml
        └── db/migration/  Flyway migrations (schema only)
```

The schema and the data are kept apart: Flyway migrations create the tables on every start, and `datainject` loads the data only when you run it. To change the schema, add a new migration (`V7__...sql`); never edit one that has already run.

## Data notes

- **Pokémon** are the 1025 base species, keyed by National Pokédex number. Alternate forms (Mega, regional) are not included.
- **Evolutions** use each Pokémon's current evolution method. Older methods are not tracked — for example, Feebas → Milotic is listed as a trade holding a Prism Scale, not the older Beauty method. Evolutions into special forms (Alolan Raichu, Vivillon patterns, Alcremie flavours) are left out, since only base species are stored.
- **Types** are each Pokémon's current typing. Older typings are not tracked — for example, Clefairy is listed as Fairy although it was Normal before Generation 6.
- **Games** follow paired releases: *Sword / Shield* is one entry, so version exclusives are not tracked. DLC are separate entries.
- **Learnsets** are stored per game, because levels differ between games of the same generation. A level of `0` means the move is learned on evolution.
- **Items per game** are only complete up to *Sword / Shield*; for later games only TMs are listed. Items per generation are complete.
- **Images** link to PokeAPI's official artwork on GitHub.

## Credits

Data comes from [PokeAPI](https://github.com/PokeAPI/pokeapi). Pokémon and all related names are trademarks of Nintendo, Game Freak and The Pokémon Company; this is a non-commercial fan project.
