@backend/CLAUDE.md

## Frontend (`frontend/`)

Next.js 16 (App Router) with TypeScript, Tailwind CSS 4 and ESLint, scaffolded with `create-next-app`. Source is in `frontend/src/` (`src/app/` for routes), and the `@/*` import alias maps to `src/*`.

- `next.config.ts` has **`cacheComponents: true`**: uncached data must sit inside `<Suspense>` (or use `'use cache'`), or the route errors. The homepage (`src/app/page.tsx`) prerenders its shell and streams the catalog; `Catalog` calls `await connection()` before fetching so `next build` never contacts the API (the build works with the backend down).
- Folder structure under `src/`, mirroring the backend's layers:
  - `types/` — API response shapes (`pokemon.ts`), safe to import anywhere.
  - `services/` — API calls, **server-only** (`import "server-only"`; importing one from a client component is a build error). `api-client.ts` has the base URL (`POKEMON_API_URL`, default `http://localhost:8080/api/v1`; see `.env.example`) and `apiGet<T>()`; one `*-service.ts` per backend controller (`pokemon-service.ts`, `type-service.ts`), each function named after the endpoint it calls. Add new endpoints as functions there.
  - `hooks/` — client-side React hooks (`"use client"`) for UI state and logic, e.g. `use-pokemon-filters.ts` (query, type, generation, filtered results, toggles, clear). Hooks don't fetch: data comes from services in server components and is passed down as props, so no CORS is needed. A hook that fetches from the browser would need CORS on the backend first.
  - `components/` — UI; `lib/` — pure helpers (`type-colors.ts`, `format.ts`).
- `components/pokemon-browser.tsx` renders the search box, type chips, generation selector and grid using `usePokemonFilters`. Search matches names accent-insensitively or an exact Pokédex number; filtering runs in the browser over the full 1025-item list.
- Artwork is `next/image` from `raw.githubusercontent.com/PokeAPI/sprites/**` (allowed in `images.remotePatterns`). Next 16 renamed `priority` to `preload`.
- Visual style is "bright adventure" (Pokémon-inspired but original: no Poké Ball, official logo font or Pokédex device). Tokens in `src/app/globals.css` `@theme`: `sky`, `sky-deep`, `sky-pale` (page background), `adventure` (red), `sun`, `ink` (deep blue used instead of black), `muted`, `line`, `card`. Fonts via `next/font/google`: Lilita One as `font-display` (headings, names, chips, buttons) and Nunito as `font-sans` (body). Signature pieces: the sky band with CSS clouds (`.sky-band`), the outlined sticker title (`.sticker-text`), thick ink borders with solid offset "game button" shadows, and type colour on card borders/art glow/chips. The floating Pikachu in `components/hero.tsx` is the only automatic animation (`.float`, off under reduced motion); cards have no hover motion because they aren't clickable yet.
- Only one `next dev` can run per folder: if the user already has `npm run dev` going on port 3000, a second one (e.g. the browser preview) exits — use the existing server instead.
- `.claude/launch.json` has a `frontend` config (`npm --prefix frontend run dev`, port 3000) for the browser preview.

Run from `frontend/`: `npm run dev` (http://localhost:3000), `npm run build`, `npm run lint`. The dev machine has Node 20.11.1; Next 16 needs >= 20.9, but some dev dependencies warn that they want >= 20.19 — upgrade Node if tooling starts failing.

This Next.js version may differ from what you know: before writing frontend code, read the relevant guide in `frontend/node_modules/next/dist/docs/` (see `frontend/AGENTS.md`, which `next dev` regenerates).

Endpoints are documented in `backend/docs/api/`. The backend has no CORS setup, so calling the API directly from the browser (client-side `fetch`) would be blocked until it allows the frontend's origin.
