-- Main-series abilities (Static, Overgrow, ...).
CREATE TABLE ability (
    id         INTEGER     PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,  -- not unique: "As One" has one version per Calyrex rider form
    generation SMALLINT    NOT NULL,  -- generation the ability was introduced in
    effect     TEXT                   -- short description of what it does
);

-- Middle table: which abilities each Pokémon can have.
-- slot 1 and 2 are regular abilities; slot 3 is the hidden ability (is_hidden = TRUE).
CREATE TABLE pokemon_ability (
    pokemon_id INTEGER  NOT NULL REFERENCES pokemon (id) ON DELETE CASCADE,
    slot       SMALLINT NOT NULL CHECK (slot IN (1, 2, 3)),
    ability_id INTEGER  NOT NULL REFERENCES ability (id) ON DELETE CASCADE,
    is_hidden  BOOLEAN  NOT NULL,
    PRIMARY KEY (pokemon_id, slot),
    UNIQUE (pokemon_id, ability_id)
);

CREATE INDEX idx_pokemon_ability_ability_id ON pokemon_ability (ability_id);
