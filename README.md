# pokemonLB

A Pokémon database you can browse: every species (1–1025) with types, base stats, artwork and evolutions, the games they appear in, what moves they learn in each game and at what level, and which moves and items exist in which games.

The project is split into two parts:

| Part | Folder | Status |
|---|---|---|
| **Backend** — REST API serving the Pokémon data | [`backend/`](backend/) | Working |
| **Frontend** — web app for browsing it | `frontend/` | Planned |

## Repository layout

```
pokemonLB/
├── README.md        this file — the whole project
├── backend/         Spring Boot API + PostgreSQL
│   ├── README.md    how to set up and run the backend
│   └── docs/api/    every endpoint, with real request and response examples
└── frontend/        (coming)
```

## Backend

A read-only REST API built with Spring Boot 4.1, Java 25, PostgreSQL 17 and Flyway.

- **Setup and running:** [`backend/README.md`](backend/README.md) — start a database, load the data once, run the API on <http://localhost:8080>.
- **API reference:** [`backend/docs/api/`](backend/docs/api/README.md) — one page per area:
  [Pokémon](backend/docs/api/pokemon.md) ·
  [Games](backend/docs/api/games.md) ·
  [Moves](backend/docs/api/moves.md) ·
  [Items](backend/docs/api/items.md) ·
  [Types](backend/docs/api/types.md)

Quick check once it's running: <http://localhost:8080/api/v1/pokemon/25> returns Pikachu with its types and stats.

## Frontend

Not started yet. It will live in `frontend/` and use the backend API above. All endpoints are `GET`, return JSON, and are listed in [`backend/docs/api/`](backend/docs/api/README.md).

## Credits

Data comes from [PokeAPI](https://github.com/PokeAPI/pokeapi). Pokémon and all related names are trademarks of Nintendo, Game Freak and The Pokémon Company; this is a non-commercial fan project.
