# Moves API

Handled by `MoveController` · base URL `http://localhost:8080/api/v1/moves`

| Method | Endpoint | Returns |
|---|---|---|
| GET | [`/api/v1/moves`](#list-moves) | All moves, optionally filtered by type |
| GET | [`/api/v1/moves/{id}`](#get-one-move) | One move |
| GET | [`/api/v1/moves/{id}/games`](#games-a-move-is-in) | Games the move exists in |

Some move ids: Thunderbolt `85`, Thunder Wave `86`, Flamethrower `53`, Surf `57`, Earthquake `89`.

For which Pokémon learn a move and how, see [`/api/v1/pokemon/{id}/moves`](pokemon.md#learnset-in-a-game).

### Move fields

| Field | Meaning |
|---|---|
| `type` | `"Electric"`, `"Fire"`, … |
| `damageClass` | `"Physical"`, `"Special"` or `"Status"` |
| `power` | Base power; `null` for status moves and moves with variable power |
| `accuracy` | Percent; `null` for moves that never miss |
| `pp` | Power points |
| `priority` | Turn priority; `0` is normal, higher goes first |
| `generation` | Generation the move was introduced in |

---

## List moves

`GET /api/v1/moves`

| Query parameter | Required | Example | Effect |
|---|---|---|---|
| `type` | no | `Electric` | Only moves of that type (not case-sensitive) |

Sorted by name.

```bash
curl http://localhost:8080/api/v1/moves
```

Returns all **920** moves. First 3 shown (names starting with a digit sort first):

```json
[
  {
    "id": 719,
    "name": "10,000,000 Volt Thunderbolt",
    "type": "Electric",
    "damageClass": "Special",
    "power": 195,
    "accuracy": null,
    "pp": 1,
    "priority": 0,
    "generation": 7
  },
  {
    "id": 71,
    "name": "Absorb",
    "type": "Grass",
    "damageClass": "Special",
    "power": 20,
    "accuracy": 100,
    "pp": 25,
    "priority": 0,
    "generation": 1
  },
  {
    "id": 709,
    "name": "Accelerock",
    "type": "Rock",
    "damageClass": "Physical",
    "power": 40,
    "accuracy": 100,
    "pp": 20,
    "priority": 1,
    "generation": 7
  }
]
```

### Only Electric moves

```bash
curl "http://localhost:8080/api/v1/moves?type=Electric"
```

Returns **49** moves (`?type=electric` gives the same result). First 2 shown:

```json
[
  {
    "id": 719,
    "name": "10,000,000 Volt Thunderbolt",
    "type": "Electric",
    "damageClass": "Special",
    "power": 195,
    "accuracy": null,
    "pp": 1,
    "priority": 0,
    "generation": 7
  },
  {
    "id": 783,
    "name": "Aura Wheel",
    "type": "Electric",
    "damageClass": "Physical",
    "power": 110,
    "accuracy": 100,
    "pp": 10,
    "priority": 0,
    "generation": 8
  }
]
```

An unknown type returns an empty list, not an error:

```bash
curl "http://localhost:8080/api/v1/moves?type=Banana"
```

```json
[]
```

---

## Get one move

`GET /api/v1/moves/{id}`

```bash
curl http://localhost:8080/api/v1/moves/85
```

```json
{
  "id": 85,
  "name": "Thunderbolt",
  "type": "Electric",
  "damageClass": "Special",
  "power": 90,
  "accuracy": 100,
  "pp": 15,
  "priority": 0,
  "generation": 1
}
```

A status move has no power:

```bash
curl http://localhost:8080/api/v1/moves/86
```

```json
{
  "id": 86,
  "name": "Thunder Wave",
  "type": "Electric",
  "damageClass": "Status",
  "power": null,
  "accuracy": 90,
  "pp": 20,
  "priority": 0,
  "generation": 1
}
```

---

## Games a move is in

`GET /api/v1/moves/{id}/games`

Games where the move exists (some Pokémon learns it, a TM teaches it, or it is in the game's move list). Sorted by generation, then game id. DLC count as their base game.

```bash
curl http://localhost:8080/api/v1/moves/85/games
```

Thunderbolt is in **27** games, from Red / Blue to Champions. First 4 shown:

```json
[
  { "id": 1, "name": "Red / Blue", "generation": 1 },
  { "id": 2, "name": "Yellow", "generation": 1 },
  { "id": 28, "name": "Red / Green (Japan)", "generation": 1 },
  { "id": 29, "name": "Blue (Japan)", "generation": 1 }
]
```

---

## Errors

| Request | Status | Why |
|---|---|---|
| `GET /api/v1/moves/9999` | 404 | No move with that id (same for `/games` under it) |
| `GET /api/v1/moves/abc` | 400 | Id is not a number |

```json
{
  "detail": "Move 9999 not found",
  "instance": "/api/v1/moves/9999",
  "status": 404,
  "title": "Not Found"
}
```
