import "server-only";

// Shared HTTP client for the backend API. Server-only: requests go from the Next.js
// server to the API, so the browser never calls the backend directly (no CORS needed).

/** Backend base URL. Override with POKEMON_API_URL, e.g. when the API runs on another port. */
export const API_URL = process.env.POKEMON_API_URL ?? "http://localhost:8080/api/v1";

/** GET a path under the API base URL and parse the JSON body. Throws on non-2xx responses. */
export async function apiGet<T>(path: string): Promise<T> {
  const response = await fetch(`${API_URL}${path}`);
  if (!response.ok) {
    throw new Error(`GET ${path} returned ${response.status}`);
  }
  return response.json() as Promise<T>;
}
