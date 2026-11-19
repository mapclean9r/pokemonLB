import Image from "next/image";
import { TypeBadge } from "@/components/type-badge";
import { formatDexNumber } from "@/lib/format";
import { typeColor } from "@/lib/type-colors";
import type { PokemonSummary } from "@/types/pokemon";

/** A glow in the primary type behind the artwork, fading into the secondary type (if any). */
function artBackground(types: string[]): string {
  const [primary, secondary = primary] = types;
  const mix = (type: string, percent: number) => `color-mix(in oklab, ${typeColor(type)} ${percent}%, white)`;
  return `radial-gradient(circle at 50% 58%, ${mix(primary, 55)} 0%, ${mix(primary, 30)} 38%, ${mix(secondary, 14)} 72%)`;
}

export function PokemonCard({ pokemon, eager }: { pokemon: PokemonSummary; eager: boolean }) {
  return (
    <article
      className="h-full overflow-hidden rounded-2xl border-[3px] bg-card"
      style={{ borderColor: typeColor(pokemon.types[0]) }}
    >
      <div className="relative aspect-square" style={{ background: artBackground(pokemon.types) }}>
        <span className="absolute top-2 left-2 z-10 rounded-full bg-ink px-2 py-0.5 text-xs font-extrabold text-white tabular-nums">
          {formatDexNumber(pokemon.id)}
        </span>
        <Image
          src={pokemon.imageUrl}
          alt=""
          fill
          sizes="(min-width: 1024px) 200px, (min-width: 640px) 30vw, 45vw"
          loading={eager ? "eager" : "lazy"}
          className="object-contain p-3"
        />
      </div>
      <div className="px-3 pt-2 pb-3">
        <h2 className="truncate font-display text-xl tracking-wide">{pokemon.name}</h2>
        <ul className="mt-1.5 flex flex-wrap gap-1.5" aria-label="Types">
          {pokemon.types.map((type) => (
            <li key={type}>
              <TypeBadge type={type} />
            </li>
          ))}
        </ul>
      </div>
    </article>
  );
}
