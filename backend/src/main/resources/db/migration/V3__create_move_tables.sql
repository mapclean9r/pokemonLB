CREATE TABLE move (
    id           INTEGER     PRIMARY KEY,
    name         VARCHAR(50) NOT NULL,  -- not unique: Z-Moves have a physical and a special version
    type         VARCHAR(20) NOT NULL,
    damage_class VARCHAR(10) NOT NULL,  -- Physical, Special or Status
    power        SMALLINT,              -- NULL for status / variable-power moves
    accuracy     SMALLINT,              -- NULL for moves that never miss
    pp           SMALLINT,
    priority     SMALLINT    NOT NULL,
    generation   SMALLINT    NOT NULL   -- generation the move was introduced in
);

CREATE TABLE move_learn_method (
    id          INTEGER     PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Middle table: how each Pokémon learns each move, per game.
-- level is the level for 'Level up' (0 = learned on evolution); 0 for every other method.
CREATE TABLE pokemon_move (
    pokemon_id      INTEGER  NOT NULL REFERENCES pokemon (id) ON DELETE CASCADE,
    game_id         INTEGER  NOT NULL REFERENCES game (id) ON DELETE CASCADE,
    move_id         INTEGER  NOT NULL REFERENCES move (id) ON DELETE CASCADE,
    learn_method_id INTEGER  NOT NULL REFERENCES move_learn_method (id),
    level           SMALLINT NOT NULL DEFAULT 0,
    PRIMARY KEY (pokemon_id, game_id, move_id, learn_method_id, level)
);

CREATE INDEX idx_pokemon_move_move_id ON pokemon_move (move_id);
CREATE INDEX idx_pokemon_move_game_id ON pokemon_move (game_id);

-- Which moves each Pokémon can learn in which generation, how, and at what level.
-- Games in the same generation can disagree (e.g. Pikachu/Thunderbolt: 42 in Sun/Moon, 21 in Let's Go),
-- so the view shows the lowest and highest level, plus every game's level in "levels".
-- Level columns are NULL for methods other than "Level up"; 0 means learned on evolution.
CREATE VIEW pokemon_move_generation AS
SELECT pm.pokemon_id,
       pm.move_id,
       g.generation,
       pm.learn_method_id,
       MIN(pm.level) FILTER (WHERE pm.learn_method_id = 1) AS min_level,
       MAX(pm.level) FILTER (WHERE pm.learn_method_id = 1) AS max_level,
       STRING_AGG(g.name || ': ' || pm.level, ', ' ORDER BY g.release_order, pm.level)
           FILTER (WHERE pm.learn_method_id = 1)            AS levels
FROM pokemon_move pm
JOIN game g ON g.id = pm.game_id
GROUP BY pm.pokemon_id, pm.move_id, g.generation, pm.learn_method_id;
