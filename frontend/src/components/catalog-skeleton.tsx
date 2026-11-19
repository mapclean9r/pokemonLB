import { Hero } from "@/components/hero";

/** Shown while the Pokémon list loads; mirrors the browser's layout so nothing jumps. */
export function CatalogSkeleton() {
  return (
    <div aria-busy="true" aria-label="Loading Pokémon">
      <Hero>
        <div className="h-[64px] w-full rounded-full border-[3px] border-ink bg-card shadow-[0_5px_0_0_var(--color-ink)] sm:h-[72px]" />
      </Hero>
      <div className="mx-auto max-w-6xl px-4 sm:px-6">
        <div className="-mt-4 h-52 rounded-3xl border-[3px] border-ink bg-card" />
        <p className="pt-8 pb-4 font-display text-2xl tracking-wide text-muted">Loading Pokémon…</p>
        <ul className="grid grid-cols-2 gap-3 sm:grid-cols-3 sm:gap-4 lg:grid-cols-5">
          {Array.from({ length: 10 }, (_, i) => (
            <li key={i} className="aspect-[3/4] rounded-2xl border-[3px] border-line bg-card" />
          ))}
        </ul>
      </div>
    </div>
  );
}
