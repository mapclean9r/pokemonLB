-- Ids follow PokeAPI version groups. Paired versions share one row because
-- the Pokédex data is per pair (version exclusives are not tracked).
CREATE TABLE game (
    id            INTEGER      PRIMARY KEY,
    name          VARCHAR(100) NOT NULL UNIQUE,
    generation    SMALLINT     NOT NULL,
    release_order SMALLINT     NOT NULL UNIQUE
);

-- Middle table: which Pokémon appear in which game's Pokédex (many-to-many).
CREATE TABLE pokemon_game (
    pokemon_id INTEGER NOT NULL REFERENCES pokemon (id) ON DELETE CASCADE,
    game_id    INTEGER NOT NULL REFERENCES game (id) ON DELETE CASCADE,
    PRIMARY KEY (pokemon_id, game_id)
);

-- Lets "all Pokémon in game X" use an index too (the PK covers lookups by pokemon_id).
CREATE INDEX idx_pokemon_game_game_id ON pokemon_game (game_id);
