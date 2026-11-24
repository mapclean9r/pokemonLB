# API documentation

One page per controller, with real requests and the responses they return.

| Page | Controller | Base URL |
|---|---|---|
| [Pokémon](pokemon.md) | `PokemonController` | `/api/v1/pokemon` |
| [Games](games.md) | `GameController` | `/api/v1/games` |
| [Moves](moves.md) | `MoveController` | `/api/v1/moves` |
| [Items](items.md) | `ItemController` | `/api/v1/items` |
| [Types](types.md) | `TypeController` | `/api/v1/types` |
| [Abilities](abilities.md) | `AbilityController` | `/api/v1/abilities` |

## Before you start

Start the API and fill the database first (see the [main README](../../README.md#getting-started)). The examples assume the default address `http://localhost:8080`. If you started it on another port, change it in the URLs.

Every endpoint is a `GET` and returns JSON. **Every request needs the API key** in the `X-API-Key` header; the local default is `pokemonlb-dev-key` (see [Security](#security)). The examples leave the header out to stay short, so add it when you try them:

```bash
curl -H "X-API-Key: pokemonlb-dev-key" http://localhost:8080/api/v1/pokemon/25
```

A plain browser tab can't send the header, so opening the URLs directly gives `401`. Use `curl`, or a tool like Postman or the IntelliJ HTTP client.

## Conventions

- **Ids are numbers.** Pokémon ids are National Pokédex numbers (Pikachu is `25`). Game and type ids are listed in [games.md](games.md#game-ids) and [types.md](types.md#type-ids).
- **Only the Pokémon list is paged.** `GET /api/v1/pokemon` returns 20 at a time with `page`, `totalItems` and `hasNext` (see [pokemon.md](pokemon.md#list-pokémon)). Every other list endpoint returns all matching results at once (up to 2174 for all items).
- **`null` means "doesn't apply".** For example, a status move has `"power": null`. Fields are never left out of a response.
- **Example responses are real** but shortened: long lists show only their first few entries, and the page says how many there are in total.

## Errors

| Status | When | Body |
|---|---|---|
| 404 | An id doesn't exist | `{"detail": "Pokémon 9999 not found", "instance": "/api/v1/pokemon/9999", "status": 404, "title": "Not Found"}` |
| 400 | An id or number parameter isn't a number, or a required parameter is missing | `{"timestamp": "…", "status": 400, "error": "Bad Request", "path": "/api/v1/pokemon/abc"}` |
| 401 | The `X-API-Key` header is missing or wrong (checked before anything else) | empty |
| 403 | With a valid key: any method other than `GET` (`POST`, `PUT`, `DELETE`, …), or a path outside `/api/v1` | empty |

## Security

The API is read-only and protected by an API key, enforced by Spring Security (`config/SecurityConfig.java` and `config/ApiKeyFilter.java`):

- Every request must send the key in the `X-API-Key` header. The key is case-sensitive. Missing or wrong: `401`.
- The key is set in `application.yml` (`pokemonlb.api-key`), or with the `POKEMON_API_KEY` environment variable. The default, `pokemonlb-dev-key`, is for local development only: anywhere else, set a long random value.
- The frontend sends the key from its server (`POKEMON_API_KEY` in `frontend/.env.local`, same default), so browsers never see it.
- With a valid key only `GET` under `/api/v1` is allowed; everything else gets `403`.
- There are no sessions or cookies. Responses carry Spring Security's default headers, such as `X-Content-Type-Options: nosniff`, `X-Frame-Options: DENY` and `Cache-Control: no-cache, no-store, max-age=0, must-revalidate`.

A filter that matches nothing (like `?type=Banana`) is not an error: it returns `[]`.
