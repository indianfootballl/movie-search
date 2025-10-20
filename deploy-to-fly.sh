#!/bin/bash
set -euo pipefail

# Usage: ./deploy-to-fly.sh <backend-app-name> <frontend-app-name>
# If app names are omitted, defaults are used.

BACKEND_APP_NAME=${1:-movie-search-ai-backend}
FRONTEND_APP_NAME=${2:-movie-search-ai-frontend}

ROOT_DIR=$(cd "$(dirname "$0")" && pwd)

if ! command -v flyctl >/dev/null 2>&1; then
  echo "flyctl is required. Install from https://fly.io/docs/hands-on/install/" >&2
  exit 1
fi

echo "Logging into Fly..."
flyctl auth login --verbose || true

echo "Ensuring backend app: $BACKEND_APP_NAME"
pushd "$ROOT_DIR/backend" >/dev/null

# Create app if missing
flyctl apps show "$BACKEND_APP_NAME" >/dev/null 2>&1 || flyctl apps create "$BACKEND_APP_NAME"

# Set required secrets
if [ -z "${YOUTUBE_API_KEY:-}" ]; then
  echo "YOUTUBE_API_KEY env var not set. Export it before running." >&2
  exit 1
fi

echo "Setting backend secrets..."
echo "YOUTUBE_API_KEY=$YOUTUBE_API_KEY" | flyctl secrets set --app "$BACKEND_APP_NAME" --stage

echo "Deploying backend..."
flyctl deploy --app "$BACKEND_APP_NAME" --remote-only --build-only=false

BACKEND_URL="https://$BACKEND_APP_NAME.fly.dev"

popd >/dev/null

echo "Ensuring frontend app: $FRONTEND_APP_NAME"
pushd "$ROOT_DIR/frontend" >/dev/null

# Create app if missing
flyctl apps show "$FRONTEND_APP_NAME" >/dev/null 2>&1 || flyctl apps create "$FRONTEND_APP_NAME"

API_URL="$BACKEND_URL/api"
echo "Deploying frontend with REACT_APP_API_URL=$API_URL ..."

# Set an app-level env (also pass as build arg to ensure CRA compile-time substitution)
flyctl deploy \
  --app "$FRONTEND_APP_NAME" \
  --remote-only \
  --build-arg REACT_APP_API_URL="$API_URL"

FRONTEND_URL="https://$FRONTEND_APP_NAME.fly.dev"

popd >/dev/null

echo "Configuring CORS on backend to allow $FRONTEND_URL"
pushd "$ROOT_DIR/backend" >/dev/null
flyctl secrets set --app "$BACKEND_APP_NAME" CORS_ALLOWED_ORIGINS="$FRONTEND_URL" --stage
popd >/dev/null

echo "Deployment complete.\nBackend: $BACKEND_URL\nFrontend: $FRONTEND_URL"


