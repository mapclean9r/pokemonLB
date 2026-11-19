import Image from "next/image";

const HERO_ARTWORK =
  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png";

/**
 * Full-width sky band with the page title and the search box.
 * `labelFor` ties the title to the search input as its label; `children` is the input.
 */
export function Hero({ labelFor, children }: { labelFor?: string; children: React.ReactNode }) {
  const titleClass = "sticker-text block font-display text-5xl leading-none tracking-wide sm:text-7xl";
  const title = labelFor ? (
    <label htmlFor={labelFor} className={titleClass}>
      Find a Pokémon
    </label>
  ) : (
    <p className={titleClass}>Find a Pokémon</p>
  );
  return (
    <section className="sky-band overflow-hidden">
      <div className="mx-auto grid max-w-6xl items-end gap-6 px-4 pt-8 pb-10 sm:px-6 md:grid-cols-[1fr_auto] md:pt-12 md:pb-14">
        <div>
          {title}
          <p className="mt-4 max-w-md text-lg font-semibold text-white">
            Search all 1025 by name or Pokédex number, then narrow it down by type and generation.
          </p>
          <div className="mt-6">{children}</div>
        </div>
        <Image
          src={HERO_ARTWORK}
          alt=""
          width={260}
          height={260}
          preload
          className="float hidden drop-shadow-[0_10px_0_rgb(27_42_107_/_0.25)] md:block"
        />
      </div>
    </section>
  );
}
