@echo off
REM Simple HTTP server for OpenAPI documentation
REM Requires: Java 18+ (Java 21 available)
REM Usage: serve.bat [port]
REM Example: serve.bat 3000

cd /d "%~dp0"

REM 포트 인자가 있으면 사용하고, 없으면 기본값 3000 사용
if "%1"=="" (
    set PORT=3000
) else (
    set PORT=%1
)

echo Starting HTTP server on http://localhost:%PORT%
echo Open your browser to: http://localhost:%PORT%
echo Press Ctrl+C to stop the server
echo.

jwebserver -p %PORT%
