-- Empties every data table so the injection can be re-run from scratch.
-- CASCADE also clears pokemon_stats, pokemon_game, pokemon_move, pokemon_type and pokemon_evolution.
TRUNCATE TABLE
    pokemon,
    type,
    game,
    move,
    move_learn_method,
    move_game_availability,
    item_game_availability,
    item_generation_availability
CASCADE;
