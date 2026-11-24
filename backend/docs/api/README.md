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

Every endpoint is a `GET` and returns JSON, so you can also open the URLs in a browser.

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

A filter that matches nothing (like `?type=Banana`) is not an error: it returns `[]`.
