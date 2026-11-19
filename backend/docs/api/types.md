# Types API

Handled by `TypeController` · base URL `http://localhost:8080/api/v1/types`

| Method | Endpoint | Returns |
|---|---|---|
| GET | [`/api/v1/types`](#list-types) | All 18 types |
| GET | [`/api/v1/types/{id}`](#get-one-type) | One type |
| GET | [`/api/v1/types/{id}/pokemon`](#pokémon-of-a-type) | Pokémon with that type |

`generation` is the generation the type was introduced in.

## Type ids

| id | Type | Gen | | id | Type | Gen |
|---|---|---|---|---|---|---|
| 1 | Normal | 1 | | 10 | Fire | 1 |
| 2 | Fighting | 1 | | 11 | Water | 1 |
| 3 | Flying | 1 | | 12 | Grass | 1 |
| 4 | Poison | 1 | | 13 | Electric | 1 |
| 5 | Ground | 1 | | 14 | Psychic | 1 |
| 6 | Rock | 1 | | 15 | Ice | 1 |
| 7 | Bug | 1 | | 16 | Dragon | 1 |
| 8 | Ghost | 1 | | 17 | Dark | 2 |
| 9 | Steel | 2 | | 18 | Fairy | 6 |

---

## List types

`GET /api/v1/types`

```bash
curl http://localhost:8080/api/v1/types
```

Returns all **18** types, sorted by id (the table above). First 3 shown:

```json
[
  { "id": 1, "name": "Normal", "generation": 1 },
  { "id": 2, "name": "Fighting", "generation": 1 },
  { "id": 3, "name": "Flying", "generation": 1 }
]
```

---

## Get one type

`GET /api/v1/types/{id}`

```bash
curl http://localhost:8080/api/v1/types/10
```

```json
{
  "id": 10,
  "name": "Fire",
  "generation": 1
}
```

---

## Pokémon of a type

`GET /api/v1/types/{id}/pokemon`

Every Pokémon that has the type as its primary **or** secondary type, sorted by Pokédex number. Each Pokémon's `types` lists its primary type first.

```bash
curl http://localhost:8080/api/v1/types/10/pokemon
```

**81** Pokémon are Fire type. First 2 shown:

```json
[
  {
    "id": 4,
    "name": "Charmander",
    "generation": 1,
    "types": ["Fire"],
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png"
  },
  {
    "id": 5,
    "name": "Charmeleon",
    "generation": 1,
    "types": ["Fire"],
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png"
  }
]
```

Flying (`/api/v1/types/3/pokemon`) returns 109 Pokémon, including Charizard, whose types are `["Fire", "Flying"]` — Flying is its secondary type.

Types are each Pokémon's **current** typing. Older typings aren't tracked, so Clefairy is listed as Fairy, although it was Normal before Generation 6.

---

## Errors

| Request | Status | Why |
|---|---|---|
| `GET /api/v1/types/99` | 404 | No type with that id (same for `/pokemon` under it) |
| `GET /api/v1/types/fire` | 400 | Id is not a number — use `10`, not the name |

```json
{
  "detail": "Type 99 not found",
  "instance": "/api/v1/types/99",
  "status": 404,
  "title": "Not Found"
}
```
