import "server-only";

// Shared HTTP client for the backend API. Server-only: requests go from the Next.js
// server to the API, so the browser never calls the backend directly (no CORS needed)
// and never sees the API key.

/** Backend base URL. Override with POKEMON_API_URL, e.g. when the API runs on another port. */
export const API_URL = process.env.POKEMON_API_URL ?? "http://localhost:8080/api/v1";

/**
 * Key the backend requires in the X-API-Key header. The default matches the backend's local
 * development key; set POKEMON_API_KEY in .env.local when the backend uses another one.
 */
const API_KEY = process.env.POKEMON_API_KEY ?? "pokemonlb-dev-key";

/** GET a path under the API base URL and parse the JSON body. Throws on non-2xx responses. */
export async function apiGet<T>(path: string): Promise<T> {
  const response = await fetch(`${API_URL}${path}`, {
    headers: { "X-API-Key": API_KEY },
  });
  if (!response.ok) {
    const hint = response.status === 401 ? " (wrong or missing API key: check POKEMON_API_KEY)" : "";
    throw new Error(`GET ${path} returned ${response.status}${hint}`);
  }
  return response.json() as Promise<T>;
}
