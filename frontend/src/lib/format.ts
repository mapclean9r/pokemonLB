/** Pokédex number as shown on cards: 25 -> "#0025". */
export function formatDexNumber(id: number): string {
  return `#${String(id).padStart(4, "0")}`;
}

/** Lowercase without accents, so "flabebe" matches "Flabébé". */
export function normalizeText(text: string): string {
  return text.normalize("NFD").replace(/[̀-ͯ]/g, "").toLowerCase();
}
