package com.pokemonlb.config;

/**
 * Every URL the API exposes, grouped by the controller that handles it.
 * Each group's BASE goes on the controller's @RequestMapping; the other constants go on its
 * @GetMapping methods and are relative to BASE.
 */
public final class ApiUrls {

    public static final String API = "/api/v1";

    // ---------------------------------------------------------------
    // PokemonController
    // ---------------------------------------------------------------
    public static final class Pokemon {
        public static final String BASE = API + "/pokemon";        // GET /api/v1/pokemon?generation=&gameId=
        public static final String BY_ID = "/{id}";                // GET /api/v1/pokemon/{id}
        public static final String GAMES = "/{id}/games";          // GET /api/v1/pokemon/{id}/games
        public static final String MOVES = "/{id}/moves";          // GET /api/v1/pokemon/{id}/moves?gameId=
        public static final String MOVE_LEVELS = "/{id}/moves/{moveId}/levels"; // GET /api/v1/pokemon/{id}/moves/{moveId}/levels
        public static final String EVOLUTIONS = "/{id}/evolutions"; // GET /api/v1/pokemon/{id}/evolutions
        public static final String ABILITIES = "/{id}/abilities";  // GET /api/v1/pokemon/{id}/abilities

        private Pokemon() {}
    }

    // ---------------------------------------------------------------
    // GameController
    // ---------------------------------------------------------------
    public static final class Games {
        public static final String BASE = API + "/games";          // GET /api/v1/games
        public static final String BY_ID = "/{id}";                // GET /api/v1/games/{id}
        public static final String POKEMON = "/{id}/pokemon";      // GET /api/v1/games/{id}/pokemon
        public static final String MOVES = "/{id}/moves";          // GET /api/v1/games/{id}/moves
        public static final String ITEMS = "/{id}/items";          // GET /api/v1/games/{id}/items

        private Games() {}
    }

    // ---------------------------------------------------------------
    // MoveController
    // ---------------------------------------------------------------
    public static final class Moves {
        public static final String BASE = API + "/moves";          // GET /api/v1/moves?type=
        public static final String BY_ID = "/{id}";                // GET /api/v1/moves/{id}
        public static final String GAMES = "/{id}/games";          // GET /api/v1/moves/{id}/games

        private Moves() {}
    }

    // ---------------------------------------------------------------
    // ItemController
    // ---------------------------------------------------------------
    public static final class Items {
        public static final String BASE = API + "/items";          // GET /api/v1/items?generation=
        public static final String BY_ID = "/{id}";                // GET /api/v1/items/{id}

        private Items() {}
    }

    // ---------------------------------------------------------------
    // TypeController
    // ---------------------------------------------------------------
    public static final class Types {
        public static final String BASE = API + "/types";          // GET /api/v1/types
        public static final String BY_ID = "/{id}";                // GET /api/v1/types/{id}
        public static final String POKEMON = "/{id}/pokemon";      // GET /api/v1/types/{id}/pokemon

        private Types() {}
    }

    // ---------------------------------------------------------------
    // AbilityController
    // ---------------------------------------------------------------
    public static final class Abilities {
        public static final String BASE = API + "/abilities";      // GET /api/v1/abilities?generation=
        public static final String BY_ID = "/{id}";                // GET /api/v1/abilities/{id}
        public static final String POKEMON = "/{id}/pokemon";      // GET /api/v1/abilities/{id}/pokemon

        private Abilities() {}
    }

    private ApiUrls() {}
}
