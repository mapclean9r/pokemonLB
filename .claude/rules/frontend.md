---
paths:
  - "frontend/**"
---

# Frontend (`frontend/`)

Next.js 16 (App Router) with React 19, TypeScript, Tailwind CSS 4 and ESLint, scaffolded with `create-next-app`. Source is in `frontend/src/` (`src/app/` for routes), and the `@/*` import alias maps to `src/*`.

## Commands

Run from `frontend/`: `npm run dev` (http://localhost:3000), `npm run build`, `npm run lint`, `npx tsc --noEmit` (type-check). The dev machine has Node 20.11.1; Next 16 needs >= 20.9, but some dev dependencies warn that they want >= 20.19 — upgrade Node if tooling starts failing.

Only one `next dev` can run per folder. The user usually has their own `npm run dev` on port 3000; a second one (e.g. the browser preview from `.claude/launch.json`) exits with "Another next dev server is already running" — use the existing server at http://localhost:3000 instead. Saving files hot-reloads; a restart is only needed after installing packages.

## This Next.js version differs from older ones

`frontend/AGENTS.md` (written and re-created by `next dev`, so leave it in place) says: this Next.js has breaking changes in APIs, conventions and file structure — read the relevant guide in `frontend/node_modules/next/dist/docs/` before writing code, and heed deprecation notices. Things already found there:

- `next.config.ts` has **`cacheComponents: true`**: uncached data must sit inside `<Suspense>` (or use `'use cache'` + `cacheLife`), or the route errors. Use `await connection()` (from `next/server`) to keep a fetch out of prerendering.
- `next/image`: `priority` is deprecated, use `preload` (or `loading="eager"`); remote images need `images.remotePatterns`.
- `import "server-only"` works without installing the package.
- `LayoutProps<"/">` / route types are generated globals.

## Structure under `src/`, mirroring the backend's layers

- `types/` — API response shapes (`pokemon.ts`), safe to import anywhere.
- `services/` — API calls, **server-only** (`import "server-only"`; importing one from a client component is a build error). `api-client.ts` has the base URL (`POKEMON_API_URL`, default `http://localhost:8080/api/v1`), the backend API key (`POKEMON_API_KEY`, default `pokemonlb-dev-key`, sent as `X-API-Key` on every request; never `NEXT_PUBLIC_`, so it stays on the server) and `apiGet<T>()`; see `.env.example`, which `.gitignore` explicitly un-ignores; one `*-service.ts` per backend controller (`pokemon-service.ts`, `type-service.ts`), each function named after the endpoint it calls. Add new endpoints as functions there.
- `hooks/` — client-side React hooks (`"use client"`) for UI state and logic. Hooks never call the backend directly (it has no CORS); when the browser needs data after the first render, the hook fetches this app's own route handler under `src/app/api/`, which calls a service on the server.
- `app/api/` — same-origin route handlers that proxy to services for browser-side loading, e.g. `app/api/pokemon/route.ts` (`GET /api/pokemon?page=&size=&search=&type=&generation=`, validates params → 400, backend down → 502).
- `components/` — UI; `lib/` — pure helpers (`type-colors.ts`, `format.ts`, `pokemon-query.ts`).
- Don't export non-component values (like class strings) from a `"use client"` file for use in server components: the server only receives a client reference.

## Homepage

- `src/app/page.tsx` prerenders the shell (header) and streams the catalog inside `<Suspense>` with `components/catalog-skeleton.tsx` as fallback. `Catalog` calls `await connection()` before fetching, so `next build` never contacts the API (the build works with the backend down). It loads only the **first page** (`getPokemonPage()`) plus the types. If the API is unreachable, it renders a message with the API URL and how to fix it.
- **Infinite scroll, 20 per page.** `components/pokemon-browser.tsx` combines three hooks:
  - `use-pokemon-filters.ts` — query/type/generation state; exposes `filters` with the search debounced 1 s after the user stops typing (`use-debounced-value.ts`).
  - `use-pokemon-pages.ts` — starts from the server-rendered first page; when `filters` change it reloads page 0 (list dims, count says "Searching…"); `loadMore()` appends the next page from `/api/pokemon`. Aborts superseded requests and drops responses for stale filters; `retry()` after an error.
  - `use-infinite-scroll.ts` — an `IntersectionObserver` on a marker below the grid (600 px early); re-attaches after each page so a tall screen keeps loading until filled.
  Searching and filtering now happen in the backend (`search` matches names accent-insensitively or an exact Pokédex number). `lib/pokemon-query.ts` builds the shared query string and holds `POKEMON_PAGE_SIZE`.
- Testing infinite scroll in the built-in browser only works while the pane is **visible**: a hidden pane doesn't render, so `IntersectionObserver` and `requestAnimationFrame` never fire there.
- Artwork is `next/image` from `raw.githubusercontent.com/PokeAPI/sprites/**`. Cards aren't links yet (no detail page).

## Visual style: "bright adventure"

Pokémon-inspired but original: no Poké Ball, official logo font or Pokédex device. Tokens in `src/app/globals.css` `@theme`: `sky`, `sky-deep`, `sky-pale` (page background), `adventure` (red), `sun`, `ink` (deep blue used instead of black), `muted`, `line`, `card`. Fonts via `next/font/google`: Lilita One as `font-display` (headings, names, chips, buttons) and Nunito as `font-sans` (body). Signature pieces: the sky band with CSS clouds (`.sky-band`), the outlined sticker title (`.sticker-text`), thick ink borders with solid offset "game button" shadows, and type colour on card borders, art glow and chips. The floating Pikachu in `hero.tsx` is the only automatic animation (`.float`, off under reduced motion); cards have no hover motion because they aren't clickable yet.
