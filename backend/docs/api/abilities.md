# Abilities API

Handled by `AbilityController` · base URL `http://localhost:8080/api/v1/abilities`

| Method | Endpoint | Returns |
|---|---|---|
| GET | [`/api/v1/abilities`](#list-abilities) | All abilities, optionally only those from one generation |
| GET | [`/api/v1/abilities/{id}`](#get-one-ability) | One ability |
| GET | [`/api/v1/abilities/{id}/pokemon`](#pokémon-with-an-ability) | Pokémon that can have the ability |

Some ability ids: Stench `1`, Static `9`, Lightning Rod `31`, Chlorophyll `34`, Blaze `66`, Overgrow `65`, Torrent `67`, Wonder Guard `25`.

For a Pokémon's own abilities, see [`/api/v1/pokemon/{id}/abilities`](pokemon.md#abilities).

### Ability fields

| Field | Meaning |
|---|---|
| `name` | English name. Not unique: "As One" exists twice (`266` and `267`), one per Calyrex rider form |
| `generation` | Generation the ability was introduced in (abilities started in Generation 3) |
| `effect` | Short description of what it does |

---

## List abilities

`GET /api/v1/abilities`

| Query parameter | Required | Example | Effect |
|---|---|---|---|
| `generation` | no | `9` | Only abilities introduced in that generation (3–9) |

Sorted by name.

```bash
curl http://localhost:8080/api/v1/abilities
```

Returns all **314** main-series abilities, starting with Adaptability, Aerilate and Aftermath.

### Only Generation 9

```bash
curl "http://localhost:8080/api/v1/abilities?generation=9"
```

Returns **47** abilities. First 2 shown:

```json
[
  {
    "id": 271,
    "name": "Anger Shell",
    "generation": 9,
    "effect": "When the Pokémon's HP drops below half, Anger Shell lowers its Defense and Special Defense but its Attack, Special Attack and Speed are raised."
  },
  {
    "id": 296,
    "name": "Armor Tail",
    "generation": 9,
    "effect": "Prevents the opponent from using any moves that have priority, such as Quick Attack."
  }
]
```

---

## Get one ability

`GET /api/v1/abilities/{id}`

```bash
curl http://localhost:8080/api/v1/abilities/9
```

```json
{
  "id": 9,
  "name": "Static",
  "generation": 3,
  "effect": "Has a 30% chance of paralyzing attacking Pokémon on contact."
}
```

---

## Pokémon with an ability

`GET /api/v1/abilities/{id}/pokemon`

Every Pokémon that can have the ability, sorted by Pokédex number. `hidden` is `true` when it's that Pokémon's hidden ability rather than a regular one.

```bash
curl http://localhost:8080/api/v1/abilities/31/pokemon
```

Lightning Rod is on **17** Pokémon, for 6 of them as their hidden ability. First one shown:

```json
[
  {
    "pokemon": {
      "id": 25,
      "name": "Pikachu",
      "generation": 1,
      "types": ["Electric"],
      "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"
    },
    "hidden": true
  }
]
```

The full list: Pikachu, Raichu, Cubone, Marowak, Rhyhorn, Rhydon, Goldeen, Seaking, Pichu, Electrike, Manectric, Plusle, Rhyperior, Blitzle, Zebstrika, Togedemaru, Pincurchin.

---

## Errors

| Request | Status | Why |
|---|---|---|
| `GET /api/v1/abilities/9999` | 404 | No ability with that id (same for `/pokemon` under it) |
| `GET /api/v1/abilities/static` | 400 | Id is not a number — use `9`, not the name |
| `GET /api/v1/abilities?generation=three` | 400 | Generation is not a number |

```json
{
  "detail": "Ability 9999 not found",
  "instance": "/api/v1/abilities/9999",
  "status": 404,
  "title": "Not Found"
}
```
