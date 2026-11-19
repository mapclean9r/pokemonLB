/** One colour per type, keyed by the type name the API returns. */
const TYPE_COLORS: Record<string, string> = {
  Normal: "#9c9a8e",
  Fire: "#e8743b",
  Water: "#3d8fe0",
  Electric: "#e8c02a",
  Grass: "#4fa35a",
  Ice: "#6cc7d6",
  Fighting: "#c2453a",
  Poison: "#9a55b5",
  Ground: "#c49a55",
  Flying: "#8c9be6",
  Psychic: "#e8608a",
  Bug: "#93aa2e",
  Rock: "#b19f5e",
  Ghost: "#6a5a9e",
  Dragon: "#5a4fd6",
  Dark: "#5e5048",
  Steel: "#8a9baa",
  Fairy: "#e395c7",
};

const FALLBACK_COLOR = "#9c9a8e";

export function typeColor(type: string): string {
  return TYPE_COLORS[type] ?? FALLBACK_COLOR;
}

/** Dark text on light type colours (Electric, Ice, ...), white text on the rest. */
export function typeTextColor(type: string): string {
  const hex = typeColor(type).slice(1);
  const [r, g, b] = [0, 2, 4].map((i) => parseInt(hex.slice(i, i + 2), 16) / 255);
  const luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
  return luminance > 0.55 ? "#1a2233" : "#ffffff";
}
