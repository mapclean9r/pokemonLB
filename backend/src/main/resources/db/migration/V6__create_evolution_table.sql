-- How each Pokémon evolves: one row per evolution step (from -> to), with its current method.
-- Only the conditions that apply are filled in; e.g. Bulbasaur -> Ivysaur is just
-- trigger 'Level up' + min_level 16, Pikachu -> Raichu is trigger 'Use item' + item 'Thunder Stone'.
CREATE TABLE pokemon_evolution (
    id               INTEGER      PRIMARY KEY,
    from_pokemon_id  INTEGER      NOT NULL REFERENCES pokemon (id) ON DELETE CASCADE,
    to_pokemon_id    INTEGER      NOT NULL REFERENCES pokemon (id) ON DELETE CASCADE,
    trigger          VARCHAR(100) NOT NULL,  -- 'Level up', 'Use item', 'Trade or Linking Cord', ...
    min_level        SMALLINT,               -- level it evolves at, when level-based
    item             VARCHAR(50),            -- item used on it, e.g. 'Fire Stone'
    held_item        VARCHAR(50),            -- item it must hold, e.g. 'Metal Coat'
    known_move       VARCHAR(50),            -- move it must know
    known_move_type  VARCHAR(20),            -- it must know a move of this type
    min_happiness    SMALLINT,               -- friendship needed
    time_of_day      VARCHAR(10),            -- 'Day', 'Night' or 'Dusk'
    gender           VARCHAR(10),            -- 'Female' or 'Male'
    other_conditions TEXT,                   -- anything rarer, in words (location, rain, chance, ...)
    UNIQUE (from_pokemon_id, to_pokemon_id),
    CHECK (from_pokemon_id <> to_pokemon_id)
);

CREATE INDEX idx_pokemon_evolution_to ON pokemon_evolution (to_pokemon_id);
