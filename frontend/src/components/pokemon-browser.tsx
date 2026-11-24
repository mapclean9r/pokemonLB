"use client";

import { Hero } from "@/components/hero";
import { PokemonCard } from "@/components/pokemon-card";
import { useInfiniteScroll } from "@/hooks/use-infinite-scroll";
import { usePokemonFilters } from "@/hooks/use-pokemon-filters";
import { usePokemonPages } from "@/hooks/use-pokemon-pages";
import { typeColor, typeTextColor } from "@/lib/type-colors";
import type { Page, PokemonSummary, PokemonType } from "@/types/pokemon";

const GENERATIONS = [1, 2, 3, 4, 5, 6, 7, 8, 9];
/** Cards above the fold load their artwork right away; the rest load as you scroll. */
const EAGER_CARDS = 12;

const SEARCH_INPUT_CLASS =
  "w-full rounded-full border-[3px] border-ink bg-card px-6 py-4 text-xl font-semibold shadow-[0_5px_0_0_var(--color-ink)] placeholder:font-normal placeholder:text-muted sm:text-2xl";

export function PokemonBrowser({
  initialPage,
  types,
}: {
  initialPage: Page<PokemonSummary>;
  types: PokemonType[];
}) {
  const { query, setQuery, type, toggleType, generation, toggleGeneration, filters, isFiltered, clearFilters } =
    usePokemonFilters();
  const { items, totalItems, hasNext, isLoading, isReplacing, hasError, loadMore, retry } = usePokemonPages(
    initialPage,
    filters,
  );
  const endMarkerRef = useInfiniteScroll<HTMLDivElement>(loadMore, hasNext && !hasError, items.length);

  return (
    <>
      <Hero labelFor="pokemon-search">
        <input
          id="pokemon-search"
          type="search"
          value={query}
          onChange={(event) => setQuery(event.target.value)}
          placeholder="Pikachu, Eevee or 25…"
          autoComplete="off"
          spellCheck={false}
          className={SEARCH_INPUT_CLASS}
        />
      </Hero>

      <div className="mx-auto max-w-6xl px-4 sm:px-6">
        <section className="-mt-4 rounded-3xl border-[3px] border-ink bg-card p-5 sm:p-6">
          <fieldset>
            <legend className="mb-3 font-display text-lg tracking-wide">Type</legend>
            <div className="flex flex-wrap gap-2">
              <FilterChip pressed={type === null} onClick={() => toggleType(null)}>
                All types
              </FilterChip>
              {types.map((t) => (
                <FilterChip
                  key={t.id}
                  pressed={type === t.name}
                  onClick={() => toggleType(t.name)}
                  color={typeColor(t.name)}
                  textColor={typeTextColor(t.name)}
                >
                  {t.name}
                </FilterChip>
              ))}
            </div>
          </fieldset>

          <fieldset className="mt-5">
            <legend className="mb-3 font-display text-lg tracking-wide">Generation</legend>
            <div className="flex flex-wrap gap-2">
              <GenerationButton pressed={generation === null} onClick={() => toggleGeneration(null)}>
                All
              </GenerationButton>
              {GENERATIONS.map((g) => (
                <GenerationButton key={g} pressed={generation === g} onClick={() => toggleGeneration(g)}>
                  Gen {g}
                </GenerationButton>
              ))}
            </div>
          </fieldset>
        </section>

        <div className="flex flex-wrap items-baseline justify-between gap-x-4 gap-y-1 pt-8 pb-4">
          <p className="font-display text-2xl tracking-wide tabular-nums" aria-live="polite">
            {isReplacing
              ? "Searching…"
              : isFiltered
                ? `${totalItems} ${totalItems === 1 ? "Pokémon matches" : "Pokémon match"}`
                : `All ${totalItems} Pokémon`}
          </p>
          {isFiltered && (
            <button
              type="button"
              onClick={clearFilters}
              className="font-bold text-sky-deep underline decoration-2 underline-offset-4"
            >
              Clear filters
            </button>
          )}
        </div>

        {items.length > 0 || isReplacing || hasError ? (
          <>
            <ul
              aria-busy={isLoading}
              className={`grid grid-cols-2 gap-3 transition-opacity sm:grid-cols-3 sm:gap-4 lg:grid-cols-5 ${
                isReplacing ? "opacity-50" : ""
              }`}
            >
              {items.map((p, index) => (
                <li key={p.id}>
                  <PokemonCard pokemon={p} eager={index < EAGER_CARDS} />
                </li>
              ))}
            </ul>

            {/* When this marker nears the viewport, the next page loads. */}
            <div ref={endMarkerRef} aria-hidden className="h-px" />

            <div className="flex min-h-20 flex-col items-center justify-center gap-3 py-6 text-center">
              {hasError ? (
                <>
                  <p className="text-lg font-semibold">
                    {isReplacing ? "The Pokémon list didn't load." : "The next Pokémon didn't load."}{" "}
                    Check that the backend is running.
                  </p>
                  <button
                    type="button"
                    onClick={retry}
                    className="rounded-full border-[3px] border-ink bg-adventure px-6 py-2 font-display text-lg tracking-wide text-white shadow-[0_4px_0_0_var(--color-ink)] active:translate-y-1 active:shadow-none"
                  >
                    Try again
                  </button>
                </>
              ) : isLoading && !isReplacing ? (
                <p className="font-display text-xl tracking-wide text-muted" role="status">
                  Loading more Pokémon…
                </p>
              ) : !hasNext && items.length > 0 ? (
                <p className="text-muted">
                  {items.length === 1 ? "That's the only match." : `That's all ${items.length} — you've reached the end.`}
                </p>
              ) : null}
            </div>
          </>
        ) : (
          <div className="rounded-3xl border-[3px] border-dashed border-ink/40 bg-card px-6 py-14 text-center">
            <p className="font-display text-3xl tracking-wide">No Pokémon found</p>
            <p className="mt-2 text-lg text-muted">Try another name, or remove the type or generation filter.</p>
            <button
              type="button"
              onClick={clearFilters}
              className="mt-6 rounded-full border-[3px] border-ink bg-adventure px-6 py-2.5 font-display text-lg tracking-wide text-white shadow-[0_4px_0_0_var(--color-ink)] active:translate-y-1 active:shadow-none"
            >
              Clear filters
            </button>
          </div>
        )}
      </div>
    </>
  );
}

function FilterChip({
  pressed,
  onClick,
  color,
  textColor,
  children,
}: {
  pressed: boolean;
  onClick: () => void;
  color?: string;
  textColor?: string;
  children: React.ReactNode;
}) {
  // Type chips are always filled with their colour; the selected one gets a thick outline,
  // a solid drop like a pressed game button, and a check mark.
  const style = color ? { backgroundColor: color, color: textColor } : undefined;
  return (
    <button
      type="button"
      aria-pressed={pressed}
      onClick={onClick}
      style={style}
      className={`inline-flex items-center gap-1 rounded-full border-[3px] px-3.5 py-1 font-display text-base tracking-wide transition-transform ${
        pressed ? "-translate-y-0.5 border-ink shadow-[0_3px_0_0_var(--color-ink)]" : "border-transparent hover:border-ink/40"
      } ${color ? "" : pressed ? "bg-ink text-white" : "bg-sky-pale text-ink"}`}
    >
      {pressed && <span aria-hidden>✓</span>}
      {children}
    </button>
  );
}

function GenerationButton({
  pressed,
  onClick,
  children,
}: {
  pressed: boolean;
  onClick: () => void;
  children: React.ReactNode;
}) {
  return (
    <button
      type="button"
      aria-pressed={pressed}
      onClick={onClick}
      className={`rounded-xl border-[3px] px-3.5 py-1 font-display text-base tracking-wide tabular-nums transition-transform ${
        pressed
          ? "-translate-y-0.5 border-ink bg-adventure text-white shadow-[0_3px_0_0_var(--color-ink)]"
          : "border-line bg-card hover:border-ink/40"
      }`}
    >
      {children}
    </button>
  );
}
