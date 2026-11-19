-- Standalone lookup tables: no foreign keys, names stored inline.
-- DLC is folded into its base game (e.g. The Teal Mask counts as Scarlet / Violet).

-- Every move and the games it exists in (learnable, taught by TM, or usable in battle).
CREATE TABLE move_game_availability (
    move_id    INTEGER      NOT NULL,
    move_name  VARCHAR(50)  NOT NULL,
    move_type  VARCHAR(20)  NOT NULL,
    game_id    INTEGER      NOT NULL,
    game_name  VARCHAR(100) NOT NULL,
    generation SMALLINT     NOT NULL,
    PRIMARY KEY (move_id, game_id)
);

-- Every item and the games it exists in. PokeAPI only has per-game item data up to
-- Sword / Shield (plus TMs in later games), so see item_generation_availability for full coverage.
CREATE TABLE item_game_availability (
    item_id    INTEGER      NOT NULL,
    item_name  VARCHAR(100) NOT NULL,
    category   VARCHAR(50)  NOT NULL,
    game_id    INTEGER      NOT NULL,
    game_name  VARCHAR(100) NOT NULL,
    generation SMALLINT     NOT NULL,
    PRIMARY KEY (item_id, game_id)
);

-- Every item and the generations it exists in (complete for all generations).
CREATE TABLE item_generation_availability (
    item_id    INTEGER      NOT NULL,
    item_name  VARCHAR(100) NOT NULL,
    category   VARCHAR(50)  NOT NULL,
    generation SMALLINT     NOT NULL,
    PRIMARY KEY (item_id, generation)
);

CREATE INDEX idx_move_game_availability_game ON move_game_availability (game_id);
CREATE INDEX idx_item_game_availability_game ON item_game_availability (game_id);
CREATE INDEX idx_item_generation_availability_gen ON item_generation_availability (generation);
