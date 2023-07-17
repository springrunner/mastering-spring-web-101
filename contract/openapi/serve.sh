#!/bin/bash
# Simple HTTP server for OpenAPI documentation
# Requires: Java 18+ (Java 21 available)
# Usage: ./serve.sh [port]
# Example: ./serve.sh 3000

cd "$(dirname "$0")"

# 포트 인자가 있으면 사용하고, 없으면 기본값 3000 사용
PORT=${1:-3000}

echo "Starting HTTP server on http://localhost:$PORT"
echo "Open your browser to: http://localhost:$PORT"
echo "Press Ctrl+C to stop the server"
echo ""

jwebserver -p $PORT
