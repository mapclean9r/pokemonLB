-- The 18 Pokémon types (Normal, Fire, Water, ...).
CREATE TABLE type (
    id         INTEGER     PRIMARY KEY,
    name       VARCHAR(20) NOT NULL UNIQUE,
    generation SMALLINT    NOT NULL   -- generation the type was introduced in
);

-- Middle table: each Pokémon's current typing. slot 1 = primary type, slot 2 = secondary.
CREATE TABLE pokemon_type (
    pokemon_id INTEGER  NOT NULL REFERENCES pokemon (id) ON DELETE CASCADE,
    type_id    INTEGER  NOT NULL REFERENCES type (id) ON DELETE CASCADE,
    slot       SMALLINT NOT NULL CHECK (slot IN (1, 2)),
    PRIMARY KEY (pokemon_id, slot),
    UNIQUE (pokemon_id, type_id)
);

CREATE INDEX idx_pokemon_type_type_id ON pokemon_type (type_id);
