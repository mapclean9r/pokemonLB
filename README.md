# pokemonLB

A Pokémon database you can browse: every species (1–1025) with types, base stats, artwork and evolutions, the games they appear in, what moves they learn in each game and at what level, and which moves and items exist in which games.

The project is split into two parts:

| Part | Folder | Status |
|---|---|---|
| **Backend** — REST API serving the Pokémon data | [`backend/`](backend/) | Working |
| **Frontend** — web app for browsing it | [`frontend/`](frontend/) | Homepage working |

## Repository layout

```
pokemonLB/
├── README.md        this file — the whole project
├── backend/         Spring Boot API + PostgreSQL
│   ├── README.md    how to set up and run the backend
│   └── docs/api/    every endpoint, with real request and response examples
└── frontend/        Next.js app (TypeScript, src/ folder)
    └── src/
        ├── app/         pages and layout (App Router)
        ├── components/  UI components
        ├── hooks/       React hooks for UI state, e.g. search and filters
        ├── services/    calls to the backend API (server-side only)
        ├── types/       shapes of the API responses
        └── lib/         small helpers (type colours, formatting)
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

A Next.js 16 app with TypeScript, the App Router, Tailwind CSS and ESLint. Code lives in `frontend/src/`; `@/` imports point there (`@/app/page` = `src/app/page.tsx`).

The homepage lists all 1025 Pokémon with artwork, number and types. You can search by name or Pokédex number and filter by type and generation. The data comes from the backend, so start the backend first; if it isn't running, the page says so instead of the list. If the API isn't at `http://localhost:8080/api/v1`, copy `frontend/.env.example` to `frontend/.env.local` and set `POKEMON_API_URL`.

Requires Node.js 20.9 or newer. From the `frontend/` folder:

```bash
npm install
```

```bash
npm run dev
```

Then open <http://localhost:3000>. Other scripts: `npm run build` (production build), `npm start` (serve the build), `npm run lint`.

It will use the backend API above — all endpoints are `GET`, return JSON, and are listed in [`backend/docs/api/`](backend/docs/api/README.md). Run the backend at the same time for the frontend to get data.

## Credits

Data comes from [PokeAPI](https://github.com/PokeAPI/pokeapi). Pokémon and all related names are trademarks of Nintendo, Game Freak and The Pokémon Company; this is a non-commercial fan project.
