# Knowledge graph first

A graphify knowledge graph of the backend code lives in `backend/graphify-out/`. A hook refreshes it after every file edit under `backend/`, so treat it as current.

For questions about how the codebase works — where something is defined, what calls what, how a request flows, what a change would affect — consult the graph **before** grepping or reading files. Run these from `backend/`:

```bash
graphify query "<question>" --budget 2000    # broad context for a question (add --dfs to trace one path)
graphify explain "<Symbol>"                  # a class/method and its connections
graphify path "<A>" "<B>"                    # how two symbols connect
```

`backend/graphify-out/GRAPH_REPORT.md` gives the overview: the 17 named groups, most-connected classes, and known gaps.

Then read the source files the graph points to (`src=… loc=L…`) before answering or editing. The graph is a map, not the code.

Gaps — use normal search for these:
- `.sql` files (Flyway migrations and `datainject/sql`) are not in the graph.
- Spring/Lombok annotations show up as nodes but are external library code.
- Literal strings and config values (URLs, property values) are faster to find with Grep.
