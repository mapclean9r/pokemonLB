# Knowledge graph first

A graphify knowledge graph of the **backend and frontend code** lives in `graphify-out/` at the repo root. A hook refreshes it after every Claude edit to a source file in `backend/` or `frontend/`, so treat it as current.

For questions about how the code works — where something is defined, what calls what, how a request flows from the frontend to the API, what a change would affect — consult the graph **before** grepping or reading files. Run these from the repo root (`C:\pokemonLB`):

```bash
graphify query "<question>" --budget 2000    # broad context for a question (add --dfs to trace one path)
graphify explain "<Symbol>"                  # a class/function/component and its connections
graphify path "<A>" "<B>"                    # how two symbols connect
```

`graphify-out/GRAPH_REPORT.md` gives the overview. Its groups are prefixed with the part they belong to: `Backend: …` (Java, config, Docker) and `Frontend: …` (Next.js pages, components, services, hooks, config). Node source paths start with `backend/` or `frontend/`.

Then read the source files the graph points to (`src=… loc=L…`) before answering or editing. The graph is a map, not the code.

What's in and out is set by `.graphifyignore` at the repo root. Use normal search for what the graph skips:
- Markdown (`README.md`, `backend/docs/api/`, Claude notes) — it would need an LLM to extract.
- `.sql` files: Flyway migrations and `datainject/sql` (the SQL parser isn't installed; the data files are also excluded on purpose).
- Generated folders (`node_modules/`, `.next/`, `target/`), the Maven wrapper and `package-lock.json`.
- Spring/Lombok/React imports show up as nodes but are external library code.
- Literal strings and config values (URLs, property values, Tailwind classes) are faster to find with Grep.

To rebuild from scratch (e.g. after changing `.graphifyignore`), run `/graphify .` from the repo root.
