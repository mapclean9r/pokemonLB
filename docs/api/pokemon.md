# Pokémon API

Handled by `PokemonController` · base URL `http://localhost:8080/api/v1/pokemon`

| Method | Endpoint | Returns |
|---|---|---|
| GET | [`/api/v1/pokemon`](#list-pokémon) | All Pokémon, optionally filtered by generation and/or game |
| GET | [`/api/v1/pokemon/{id}`](#get-one-pokémon) | One Pokémon with types and base stats |
| GET | [`/api/v1/pokemon/{id}/games`](#games-a-pokémon-is-in) | Games whose Pokédex includes it |
| GET | [`/api/v1/pokemon/{id}/moves?gameId=`](#learnset-in-a-game) | Its learnset in one game |
| GET | [`/api/v1/pokemon/{id}/moves/{moveId}/levels`](#level-a-move-is-learned-at-per-generation) | The level it learns a move at, per generation |
| GET | [`/api/v1/pokemon/{id}/evolutions`](#evolution-family) | Its whole evolution family |

`{id}` is the **National Pokédex number** (1–1025): Bulbasaur is `1`, Pikachu `25`, Charizard `6`. Game ids are listed in [games.md](games.md#game-ids), move ids in [moves.md](moves.md).

---

## List Pokémon

`GET /api/v1/pokemon`

| Query parameter | Required | Example | Effect |
|---|---|---|---|
| `generation` | no | `9` | Only Pokémon introduced in that generation (1–9) |
| `gameId` | no | `24` | Only Pokémon in that game's Pokédex |

Both can be combined. Results are sorted by Pokédex number. Each Pokémon's `types` lists the primary type first.

### All Pokémon

```bash
curl http://localhost:8080/api/v1/pokemon
```

Returns all **1025** Pokémon. First 2 shown:

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

### Only Generation 9

```bash
curl "http://localhost:8080/api/v1/pokemon?generation=9"
```

Returns **120** Pokémon, starting with Sprigatito:

```json
[
  {
    "id": 906,
    "name": "Sprigatito",
    "generation": 9,
    "types": ["Grass"],
    "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/906.png"
  }
]
```

### Only Pokémon in Legends: Arceus

```bash
curl "http://localhost:8080/api/v1/pokemon?gameId=24"
```

Returns **242** Pokémon (the Hisui Pokédex), starting with Pikachu (`25`) and Raichu (`26`).

### Generation 4 Pokémon in Legends: Arceus

```bash
curl "http://localhost:8080/api/v1/pokemon?generation=4&gameId=24"
```

Returns **107** Pokémon, starting with Turtwig (`387`) and Grotle (`388`).

---

## Get one Pokémon

`GET /api/v1/pokemon/{id}`

```bash
curl http://localhost:8080/api/v1/pokemon/25
```

```json
{
  "id": 25,
  "name": "Pikachu",
  "generation": 1,
  "types": ["Electric"],
  "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
  "stats": {
    "hp": 35,
    "attack": 55,
    "defense": 40,
    "specialAttack": 50,
    "specialDefense": 50,
    "speed": 90,
    "total": 320
  }
}
```

A dual-type Pokémon, Charizard:

```bash
curl http://localhost:8080/api/v1/pokemon/6
```

```json
{
  "id": 6,
  "name": "Charizard",
  "generation": 1,
  "types": ["Fire", "Flying"],
  "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
  "stats": {
    "hp": 78,
    "attack": 84,
    "defense": 78,
    "specialAttack": 109,
    "specialDefense": 85,
    "speed": 100,
    "total": 534
  }
}
```

---

## Games a Pokémon is in

`GET /api/v1/pokemon/{id}/games`

Games whose regional Pokédex includes the Pokémon, in release order.

```bash
curl http://localhost:8080/api/v1/pokemon/25/games
```

Pikachu is in **25** games. First 4 shown:

```json
[
  { "id": 28, "name": "Red / Green (Japan)", "generation": 1 },
  { "id": 29, "name": "Blue (Japan)", "generation": 1 },
  { "id": 1, "name": "Red / Blue", "generation": 1 },
  { "id": 2, "name": "Yellow", "generation": 1 }
]
```

Pikachu isn't in Black / White, because it isn't in the Unova Pokédex.

---

## Learnset in a game

`GET /api/v1/pokemon/{id}/moves?gameId={gameId}`

| Query parameter | Required | Example |
|---|---|---|
| `gameId` | **yes** | `25` (Scarlet / Violet) |

Every move the Pokémon can learn in that game. Sorted by learn method, then level, then move name. `level` is only set for `"Level up"`: `0` means it learns the move when it evolves, and it is `null` for every other method (`"Machine"`, `"Egg"`, `"Tutor"`, …).

```bash
curl "http://localhost:8080/api/v1/pokemon/25/moves?gameId=25"
```

Pikachu has **67** entries in Scarlet / Violet. A level-up move and a TM move:

```json
[
  {
    "move": {
      "id": 85,
      "name": "Thunderbolt",
      "type": "Electric",
      "damageClass": "Special",
      "power": 90,
      "accuracy": 100,
      "pp": 15,
      "priority": 0,
      "generation": 1
    },
    "learnMethod": "Level up",
    "level": 36
  },
  {
    "move": {
      "id": 97,
      "name": "Agility",
      "type": "Psychic",
      "damageClass": "Status",
      "power": null,
      "accuracy": null,
      "pp": 30,
      "priority": 0,
      "generation": 1
    },
    "learnMethod": "Machine",
    "level": null
  }
]
```

Pikachu's level-up moves in Scarlet / Violet, from this endpoint:

| Level | Moves |
|---|---|
| 1 | Charm, Growl, Nasty Plot, Nuzzle, Play Nice, Quick Attack, Sweet Kiss, Tail Whip, Thunder Shock |
| 4 | Thunder Wave |
| 8 | Double Team |
| 12 | Electro Ball |
| 16 | Feint |
| 20 | Spark |
| 24 | Agility |
| 28 | Iron Tail |
| 32 | Discharge |
| 36 | Thunderbolt |
| 40 | Light Screen |
| 44 | Thunder |

---

## Level a move is learned at, per generation

`GET /api/v1/pokemon/{id}/moves/{moveId}/levels`

For each generation where the Pokémon learns the move by leveling up, the lowest and highest level across that generation's games. They differ when games in the same generation disagree.

```bash
curl http://localhost:8080/api/v1/pokemon/25/moves/85/levels
```

When Pikachu learns Thunderbolt (move `85`):

```json
[
  { "generation": 1, "minLevel": 26, "maxLevel": 26 },
  { "generation": 2, "minLevel": 26, "maxLevel": 26 },
  { "generation": 3, "minLevel": 26, "maxLevel": 26 },
  { "generation": 4, "minLevel": 26, "maxLevel": 26 },
  { "generation": 5, "minLevel": 29, "maxLevel": 29 },
  { "generation": 6, "minLevel": 29, "maxLevel": 42 },
  { "generation": 7, "minLevel": 21, "maxLevel": 42 },
  { "generation": 8, "minLevel": 29, "maxLevel": 36 },
  { "generation": 9, "minLevel": 25, "maxLevel": 36 }
]
```

In generation 7, for example, Sun / Moon teach it at 42 and Let's Go at 21. For the exact level in one game, use the [learnset](#learnset-in-a-game) endpoint. Returns `[]` if the Pokémon never learns the move by leveling up.

---

## Evolution family

`GET /api/v1/pokemon/{id}/evolutions`

The Pokémon's whole evolution family, first stage first. You get the same list whichever member you ask for. Each step has `from`, `to` and a `trigger`. Of the condition fields, only the ones that apply are filled in; the rest are `null`.

| Field | Meaning | Example |
|---|---|---|
| `trigger` | What causes it | `"Level up"`, `"Use item"`, `"Trade or Linking Cord"` |
| `minLevel` | Level it evolves at | `16` |
| `item` | Item used on it | `"Fire Stone"` |
| `heldItem` | Item it must hold | `"Prism Scale"` |
| `knownMove` | Move it must know | `"Dragon Cheer"` |
| `knownMoveType` | It must know a move of this type | `"Fairy"` |
| `minHappiness` | Friendship needed | `160` |
| `timeOfDay` | `"Day"`, `"Night"` or `"Dusk"` | `"Night"` |
| `gender` | `"Female"` or `"Male"` | `"Female"` |
| `otherConditions` | Anything rarer, in words | `"50% chance"` |

### Level-up: Charmander's family

```bash
curl http://localhost:8080/api/v1/pokemon/5/evolutions
```

```json
[
  {
    "from": { "id": 4, "name": "Charmander", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png" },
    "to": { "id": 5, "name": "Charmeleon", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png" },
    "trigger": "Level up",
    "minLevel": 16,
    "item": null,
    "heldItem": null,
    "knownMove": null,
    "knownMoveType": null,
    "minHappiness": null,
    "timeOfDay": null,
    "gender": null,
    "otherConditions": null
  },
  {
    "from": { "id": 5, "name": "Charmeleon", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/5.png" },
    "to": { "id": 6, "name": "Charizard", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png" },
    "trigger": "Level up",
    "minLevel": 36,
    "item": null,
    "heldItem": null,
    "knownMove": null,
    "knownMoveType": null,
    "minHappiness": null,
    "timeOfDay": null,
    "gender": null,
    "otherConditions": null
  }
]
```

### Branching: Eevee

```bash
curl http://localhost:8080/api/v1/pokemon/133/evolutions
```

Returns **8** steps. Summary of the response:

| to | trigger | conditions |
|---|---|---|
| Vaporeon (134) | Use item | `item: "Water Stone"` |
| Jolteon (135) | Use item | `item: "Thunder Stone"` |
| Flareon (136) | Use item | `item: "Fire Stone"` |
| Espeon (196) | Level up | `minHappiness: 160`, `timeOfDay: "Day"` |
| Umbreon (197) | Level up | `minHappiness: 160`, `timeOfDay: "Night"` |
| Leafeon (470) | Use item | `item: "Leaf Stone"` |
| Glaceon (471) | Use item | `item: "Ice Stone"` |
| Sylveon (700) | Level up | `minHappiness: 160`, `knownMoveType: "Fairy"` |

The Espeon step in full:

```json
{
  "from": { "id": 133, "name": "Eevee", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/133.png" },
  "to": { "id": 196, "name": "Espeon", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/196.png" },
  "trigger": "Level up",
  "minLevel": null,
  "item": null,
  "heldItem": null,
  "knownMove": null,
  "knownMoveType": null,
  "minHappiness": 160,
  "timeOfDay": "Day",
  "gender": null,
  "otherConditions": null
}
```

### Trade: Feebas

```bash
curl http://localhost:8080/api/v1/pokemon/349/evolutions
```

```json
[
  {
    "from": { "id": 349, "name": "Feebas", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/349.png" },
    "to": { "id": 350, "name": "Milotic", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/350.png" },
    "trigger": "Trade or Linking Cord",
    "minLevel": null,
    "item": null,
    "heldItem": "Prism Scale",
    "knownMove": null,
    "knownMoveType": null,
    "minHappiness": null,
    "timeOfDay": null,
    "gender": null,
    "otherConditions": null
  }
]
```

### Rare conditions: Wurmple

```bash
curl http://localhost:8080/api/v1/pokemon/265/evolutions
```

Returns 4 steps: Wurmple → Silcoon and Wurmple → Cascoon (both level 7, random), then Silcoon → Beautifly and Cascoon → Dustox (both level 10). The first step:

```json
{
  "from": { "id": 265, "name": "Wurmple", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/265.png" },
  "to": { "id": 266, "name": "Silcoon", "imageUrl": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/266.png" },
  "trigger": "Level up",
  "minLevel": 7,
  "item": null,
  "heldItem": null,
  "knownMove": null,
  "knownMoveType": null,
  "minHappiness": null,
  "timeOfDay": null,
  "gender": null,
  "otherConditions": "Depends on its personality value (random); 50% chance"
}
```

### A Pokémon that doesn't evolve: Tauros

```bash
curl http://localhost:8080/api/v1/pokemon/128/evolutions
```

```json
[]
```

Evolutions use each Pokémon's **current** method; older methods (like Feebas's Beauty) and evolutions into special forms (like Alolan Raichu) aren't included.

---

## Errors

| Request | Status | Why |
|---|---|---|
| `GET /api/v1/pokemon/9999` | 404 | No Pokémon with that number |
| `GET /api/v1/pokemon/25/moves?gameId=999` | 404 | No game with that id |
| `GET /api/v1/pokemon/25/moves/9999/levels` | 404 | No move with that id |
| `GET /api/v1/pokemon/abc` | 400 | Id is not a number |
| `GET /api/v1/pokemon/25/moves` | 400 | `gameId` is missing |
| `GET /api/v1/pokemon?generation=abc` | 400 | Generation is not a number |

404 body:

```json
{
  "detail": "Pokémon 9999 not found",
  "instance": "/api/v1/pokemon/9999",
  "status": 404,
  "title": "Not Found"
}
```

400 body:

```json
{
  "timestamp": "2026-11-19T12:28:52.029Z",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/v1/pokemon/abc"
}
```
