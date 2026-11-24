import type { NextRequest } from "next/server";
import { POKEMON_PAGE_SIZE } from "@/lib/pokemon-query";
import { getPokemonPage } from "@/services/pokemon-service";
import type { PokemonFilters } from "@/types/pokemon";

// GET /api/pokemon?page=1&search=char&type=Fire&generation=1
// Same-origin proxy for the infinite scroll: the browser calls this route, and the server
// calls the backend through the service. The backend never sees browser requests, so it needs no CORS.

const MAX_PAGE_SIZE = 100;

export async function GET(request: NextRequest) {
  const params = request.nextUrl.searchParams;

  const page = toInteger(params.get("page")) ?? 0;
  const size = toInteger(params.get("size")) ?? POKEMON_PAGE_SIZE;
  const generation = toInteger(params.get("generation"));
  if (page < 0 || size < 1 || size > MAX_PAGE_SIZE || (generation !== undefined && generation < 1)) {
    return Response.json({ error: "page must be 0 or more, size 1-100, generation 1 or more" }, { status: 400 });
  }

  const filters: PokemonFilters = {
    search: params.get("search") ?? undefined,
    type: params.get("type") ?? undefined,
    generation,
  };

  try {
    return Response.json(await getPokemonPage(filters, page, size));
  } catch (error) {
    console.error("Could not load a page of Pokémon from the API:", error);
    return Response.json({ error: "The Pokémon API didn't respond. Check that the backend is running." }, { status: 502 });
  }
}

/** Whole number from a query parameter, or undefined when missing or not a whole number. */
function toInteger(value: string | null): number | undefined {
  if (value === null || !/^-?\d+$/.test(value)) return undefined;
  return Number(value);
}
