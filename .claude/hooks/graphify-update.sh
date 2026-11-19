#!/usr/bin/env bash
# PostToolUse hook: after Claude writes or edits a file under backend/, refresh the
# graphify code graph in backend/graphify-out (AST only, no LLM, ~2 s).
# Changes to the graph's own output folder are ignored so the hook never loops.

payload=$(cat)

# Pull tool_input.file_path out of the JSON (Windows paths arrive with escaped backslashes).
file=$(printf '%s' "$payload" | sed -n 's/.*"file_path"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/p' | head -n 1)
file=${file//\\\\//}   # \\ -> /
file=${file//\\//}     # \  -> /

case "$file" in
  */backend/graphify-out/*) exit 0 ;;
  */backend/*) ;;
  *) exit 0 ;;
esac

cd "${CLAUDE_PROJECT_DIR:-$(dirname "$0")/../..}/backend" || exit 0
graphify update
