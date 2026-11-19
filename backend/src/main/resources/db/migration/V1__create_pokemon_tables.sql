CREATE TABLE pokemon (
    id         INTEGER      PRIMARY KEY, -- National Pokédex number
    name       VARCHAR(50)  NOT NULL UNIQUE,
    generation SMALLINT     NOT NULL,
    image_url  VARCHAR(255) NOT NULL     -- official artwork (PNG)
);

CREATE TABLE pokemon_stats (
    pokemon_id      INTEGER  PRIMARY KEY REFERENCES pokemon (id) ON DELETE CASCADE,
    hp              SMALLINT NOT NULL CHECK (hp > 0),
    attack          SMALLINT NOT NULL CHECK (attack > 0),
    defense         SMALLINT NOT NULL CHECK (defense > 0),
    special_attack  SMALLINT NOT NULL CHECK (special_attack > 0),
    special_defense SMALLINT NOT NULL CHECK (special_defense > 0),
    speed           SMALLINT NOT NULL CHECK (speed > 0),
    total           SMALLINT GENERATED ALWAYS AS
                    (hp + attack + defense + special_attack + special_defense + speed) STORED
);
