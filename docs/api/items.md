# Items API

Handled by `ItemController` · base URL `http://localhost:8080/api/v1/items`

| Method | Endpoint | Returns |
|---|---|---|
| GET | [`/api/v1/items`](#list-items) | All items, optionally only those in one generation |
| GET | [`/api/v1/items/{id}`](#get-one-item) | One item, with the generations and games it is in |

Some item ids: Master Ball `1`, Poké Ball `4`, Potion `17`, Revive `28`, Rare Candy `50`.

For the items in one game, see [`/api/v1/games/{id}/items`](games.md#items-in-a-game).

> **Generations are complete, games are not.** Per-game item data only exists up to Sword / Shield; later games only list their TMs. So an item's `generations` list is reliable, while its `games` list stops at Sword / Shield for most items.

---

## List items

`GET /api/v1/items`

| Query parameter | Required | Example | Effect |
|---|---|---|---|
| `generation` | no | `9` | Only items that exist in that generation (1–9) |

Sorted by name. Names starting with ★ (Dynamax crystals) sort before letters.

```bash
curl http://localhost:8080/api/v1/items
```

Returns all **2174** items. First 3 shown:

```json
[
  { "id": 1331, "name": "★And15", "category": "Dynamax crystals" },
  { "id": 1332, "name": "★And337", "category": "Dynamax crystals" },
  { "id": 1334, "name": "★And390", "category": "Dynamax crystals" }
]
```

### Only Generation 9

```bash
curl "http://localhost:8080/api/v1/items?generation=9"
```

Returns **2125** items, in the same format.

---

## Get one item

`GET /api/v1/items/{id}`

```bash
curl http://localhost:8080/api/v1/items/1
```

The Master Ball is in all 9 generations and **13** games. First 3 games shown:

```json
{
  "id": 1,
  "name": "Master Ball",
  "category": "Standard balls",
  "generations": [1, 2, 3, 4, 5, 6, 7, 8, 9],
  "games": [
    { "id": 5, "name": "Ruby / Sapphire", "generation": 3 },
    { "id": 6, "name": "Emerald", "generation": 3 },
    { "id": 7, "name": "FireRed / LeafGreen", "generation": 3 }
  ]
}
```

The 13 games run from Ruby / Sapphire to Sword / Shield. Generations 1, 2 and 9 appear in `generations` but have no entries in `games`, because of the per-game data gap described above.

---

## Errors

| Request | Status | Why |
|---|---|---|
| `GET /api/v1/items/99999` | 404 | No item with that id |
| `GET /api/v1/items/abc` | 400 | Id is not a number |
| `GET /api/v1/items?generation=nine` | 400 | Generation is not a number |

```json
{
  "detail": "Item 99999 not found",
  "instance": "/api/v1/items/99999",
  "status": 404,
  "title": "Not Found"
}
```
