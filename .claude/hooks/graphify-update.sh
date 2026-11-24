#!/usr/bin/env bash
# PostToolUse hook: after Claude writes or edits a source file in backend/ or frontend/,
# refresh the graphify knowledge graph in graphify-out/ at the repo root
# (AST only, no LLM, a few seconds). What the graph covers is set in .graphifyignore.

payload=$(cat)

# Pull tool_input.file_path out of the JSON (Windows paths arrive with escaped backslashes).
file=$(printf '%s' "$payload" | sed -n 's/.*"file_path"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p' | head -n 1)
file=${file//\\\\//}   # \\ -> /
file=${file//\\//}     # \  -> /

case "$file" in
  # Generated output and dependencies: never trigger a rebuild (and never loop on the graph itself).
  */graphify-out/* | */node_modules/* | */.next/* | */target/*) exit 0 ;;
  # Markdown isn't in the graph (needs an LLM), so editing it changes nothing.
  *.md) exit 0 ;;
  */backend/* | */frontend/*) ;;
  *) exit 0 ;;
esac

cd "${CLAUDE_PROJECT_DIR:-$(dirname "$0")/../..}" || exit 0
graphify update
