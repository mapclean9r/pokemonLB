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
- `services/` — API calls, **server-only** (`import "server-only"`; importing one from a client component is a build error). `api-client.ts` has the base URL (`POKEMON_API_URL`, default `http://localhost:8080/api/v1`; see `.env.example`, which `.gitignore` explicitly un-ignores) and `apiGet<T>()`; one `*-service.ts` per backend controller (`pokemon-service.ts`, `type-service.ts`), each function named after the endpoint it calls. Add new endpoints as functions there.
- `hooks/` — client-side React hooks (`"use client"`) for UI state and logic, e.g. `use-pokemon-filters.ts` (query, type, generation, filtered results, toggles, clear). Hooks don't fetch: data comes from services in server components and is passed down as props, so the backend needs no CORS. A hook that fetches from the browser would need CORS on the backend first.
- `components/` — UI; `lib/` — pure helpers (`type-colors.ts`, `format.ts`).
- Don't export non-component values (like class strings) from a `"use client"` file for use in server components: the server only receives a client reference.

## Homepage

- `src/app/page.tsx` prerenders the shell (header) and streams the catalog inside `<Suspense>` with `components/catalog-skeleton.tsx` as fallback. `Catalog` calls `await connection()` before fetching, so `next build` never contacts the API (the build works with the backend down). If the API is unreachable, it renders a message with the API URL and how to fix it.
- `components/pokemon-browser.tsx` renders `components/hero.tsx` (with the search input), the type chips, generation buttons and the grid using `usePokemonFilters`. Search matches names accent-insensitively or an exact Pokédex number (`25` or `#25`); filtering runs in the browser over the full 1025-item list.
- Artwork is `next/image` from `raw.githubusercontent.com/PokeAPI/sprites/**`. Cards aren't links yet (no detail page).

## Visual style: "bright adventure"

Pokémon-inspired but original: no Poké Ball, official logo font or Pokédex device. Tokens in `src/app/globals.css` `@theme`: `sky`, `sky-deep`, `sky-pale` (page background), `adventure` (red), `sun`, `ink` (deep blue used instead of black), `muted`, `line`, `card`. Fonts via `next/font/google`: Lilita One as `font-display` (headings, names, chips, buttons) and Nunito as `font-sans` (body). Signature pieces: the sky band with CSS clouds (`.sky-band`), the outlined sticker title (`.sticker-text`), thick ink borders with solid offset "game button" shadows, and type colour on card borders, art glow and chips. The floating Pikachu in `hero.tsx` is the only automatic animation (`.float`, off under reduced motion); cards have no hover motion because they aren't clickable yet.
