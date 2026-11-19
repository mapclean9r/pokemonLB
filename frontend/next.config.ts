import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  cacheComponents: true,
  images: {
    // Pokémon artwork URLs from the backend point at PokeAPI's sprite repository.
    remotePatterns: [new URL("https://raw.githubusercontent.com/PokeAPI/sprites/**")],
  },
  partialPrefetching: true,
  turbopack: {
    rules: {
      "*.css": {
        loaders: ["@tailwindcss/turbopack"],
        as: "*.css",
      },
    },
  },
};

export default nextConfig;
