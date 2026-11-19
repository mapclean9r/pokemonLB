import { connection } from "next/server";
import { Suspense } from "react";
import { CatalogSkeleton } from "@/components/catalog-skeleton";
import { PokemonBrowser } from "@/components/pokemon-browser";
import { API_URL } from "@/services/api-client";
import { getAllPokemon } from "@/services/pokemon-service";
import { getTypes } from "@/services/type-service";

export default function HomePage() {
  return (
    <div className="pb-24">
      <header className="bg-sky-deep">
        <div className="mx-auto max-w-6xl px-4 py-3 sm:px-6">
          <p className="font-display text-2xl tracking-wide text-white">pokemonLB</p>
        </div>
      </header>
      <main>
        {/* The shell above is prerendered; the list streams in from the API on each request. */}
        <Suspense fallback={<CatalogSkeleton />}>
          <Catalog />
        </Suspense>
      </main>
    </div>
  );
}

async function Catalog() {
  // Load the list at request time only, so building the app never needs the backend.
  await connection();
  const data = await loadCatalog();
  if (!data) return <ApiUnavailable />;
  return <PokemonBrowser pokemon={data.pokemon} types={data.types} />;
}

/** Both lists, or null when the API can't be reached. */
async function loadCatalog() {
  try {
    const [pokemon, types] = await Promise.all([getAllPokemon(), getTypes()]);
    return { pokemon, types };
  } catch (error) {
    console.error("Could not load Pokémon from the API:", error);
    return null;
  }
}

function ApiUnavailable() {
  return (
    <section className="mx-auto max-w-2xl px-4 pt-16 sm:px-6">
      <h1 className="font-display text-4xl tracking-wide">The Pokémon list didn&apos;t load</h1>
      <p className="mt-3 text-lg text-muted">
        The frontend couldn&apos;t reach the backend API at <code className="text-ink">{API_URL}</code>.
      </p>
      <ol className="mt-5 list-decimal space-y-1.5 pl-5">
        <li>Start the backend (see <code>backend/README.md</code>).</li>
        <li>
          If it runs on another address, set <code>POKEMON_API_URL</code> in <code>frontend/.env.local</code>.
        </li>
        <li>Reload this page.</li>
      </ol>
    </section>
  );
}
