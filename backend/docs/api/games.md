# Games API

Handled by `GameController` · base URL `http://localhost:8080/api/v1/games`

| Method | Endpoint | Returns |
|---|---|---|
| GET | [`/api/v1/games`](#list-games) | All games in release order |
| GET | [`/api/v1/games/{id}`](#get-one-game) | One game |
| GET | [`/api/v1/games/{id}/pokemon`](#pokémon-in-a-game) | Pokémon in the game's Pokédex |
| GET | [`/api/v1/games/{id}/moves`](#moves-in-a-game) | Moves that exist in the game |
| GET | [`/api/v1/games/{id}/items`](#items-in-a-game) | Items in the game |

Paired versions are one game (`"Sword / Shield"`), so version exclusives aren't tracked. DLC are separate games. Ids are **not** in release order; see the table below.

## Game ids

| id | Game | Gen |
|---|---|---|
| 28 | Red / Green (Japan) | 1 |
| 29 | Blue (Japan) | 1 |
| 1 | Red / Blue | 1 |
| 2 | Yellow | 1 |
| 3 | Gold / Silver | 2 |
| 4 | Crystal | 2 |
| 5 | Ruby / Sapphire | 3 |
| 6 | Emerald | 3 |
| 12 | Colosseum | 3 |
| 13 | XD: Gale of Darkness | 3 |
| 7 | FireRed / LeafGreen | 3 |
| 8 | Diamond / Pearl | 4 |
| 9 | Platinum | 4 |
| 10 | HeartGold / SoulSilver | 4 |
| 11 | Black / White | 5 |
| 14 | Black 2 / White 2 | 5 |
| 15 | X / Y | 6 |
| 16 | Omega Ruby / Alpha Sapphire | 6 |
| 17 | Sun / Moon | 7 |
| 18 | Ultra Sun / Ultra Moon | 7 |
| 19 | Let's Go, Pikachu! / Let's Go, Eevee! | 7 |
| 20 | Sword / Shield | 8 |
| 21 | Sword / Shield: The Isle of Armor | 8 |
| 22 | Sword / Shield: The Crown Tundra | 8 |
| 23 | Brilliant Diamond / Shining Pearl | 8 |
| 24 | Legends: Arceus | 8 |
| 25 | Scarlet / Violet | 9 |
| 26 | Scarlet / Violet: The Teal Mask | 9 |
| 27 | Scarlet / Violet: The Indigo Disk | 9 |
| 30 | Legends: Z-A | 9 |
| 31 | Legends: Z-A: Mega Dimension | 9 |
| 32 | Champions | 9 |

---

## List games

`GET /api/v1/games`

```bash
curl http://localhost:8080/api/v1/games
```

Returns all **32** games in release order (the table above). First 4 shown:

```json
[
  { "id": 28, "name": "Red / Green (Japan)", "generation": 1 },
  { "id": 29, "name": "Blue (Japan)", "generation": 1 },
  { "id": 1, "name": "Red / Blue", "generation": 1 },
  { "id": 2, "name": "Yellow", "generation": 1 }
]
```

---

## Get one game

`GET /api/v1/games/{id}`

```bash
curl http://localhost:8080/api/v1/games/25
```

```json
{
  "id": 25,
  "name": "Scarlet / Violet",
  "generation": 9
}
```

---

## Pokémon in a game

`GET /api/v1/games/{id}/pokemon`

Pokémon in the game's regional Pokédex(es), sorted by National Pokédex number. For a game that has DLC, this includes the DLC Pokédexes.

```bash
curl http://localhost:8080/api/v1/games/25/pokemon
```

Scarlet / Violet has **664** Pokémon. First 2 shown:

```json
[
  {
    "id": 1,
    "name": "Bulbasaur",
    "generation": 1,
    "types": ["Grass", "Poison"],
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
  },
  {
    "id": 2,
    "name": "Ivysaur",
    "generation": 1,
    "types": ["Grass", "Poison"],
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png"
  }
]
```

To filter this further, use [`/api/v1/pokemon?gameId=25&generation=9`](pokemon.md#list-pokémon).

---

## Moves in a game

`GET /api/v1/games/{id}/moves`

Every move that exists in the game, sorted by name. For full move details, call [`/api/v1/moves/{id}`](moves.md#get-one-move).

```bash
curl http://localhost:8080/api/v1/games/25/moves
```

Scarlet / Violet has **687** moves. First 4 shown:

```json
[
  { "id": 71, "name": "Absorb", "type": "Grass" },
  { "id": 709, "name": "Accelerock", "type": "Rock" },
  { "id": 51, "name": "Acid", "type": "Poison" },
  { "id": 151, "name": "Acid Armor", "type": "Poison" }
]
```

---

## Items in a game

`GET /api/v1/games/{id}/items`

Items in the game, sorted by name.

> **Incomplete for newer games.** Full item lists only exist up to Sword / Shield. For later games only TMs are listed: Scarlet / Violet returns **229** items, all TMs. For a complete list per generation, use [`/api/v1/items?generation=`](items.md#list-items).

```bash
curl http://localhost:8080/api/v1/games/20/items
```

Sword / Shield has **1554** items. Names starting with ★ sort first, so the list begins with Dynamax crystals:

```json
[
  { "id": 1331, "name": "★And15", "category": "Dynamax crystals" },
  { "id": 1332, "name": "★And337", "category": "Dynamax crystals" },
  { "id": 1334, "name": "★And390", "category": "Dynamax crystals" }
]
```

Scarlet / Violet (TMs only):

```bash
curl http://localhost:8080/api/v1/games/25/items
```

```json
[
  { "id": 305, "name": "TM01", "category": "All machines" },
  { "id": 306, "name": "TM02", "category": "All machines" },
  { "id": 307, "name": "TM03", "category": "All machines" }
]
```

---

## Errors

| Request | Status | Why |
|---|---|---|
| `GET /api/v1/games/999` | 404 | No game with that id (same for `/pokemon`, `/moves`, `/items` under it) |
| `GET /api/v1/games/abc` | 400 | Id is not a number |

```json
{
  "detail": "Game 999 not found",
  "instance": "/api/v1/games/999",
  "status": 404,
  "title": "Not Found"
}
```
