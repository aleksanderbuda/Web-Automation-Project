set "PROJECT_DIR=%~dp0"
set "DOCKER_DIR=%PROJECT_DIR%"
set "APP_PATH=%DOCKER_DIR%\app"
cd %DOCKER_DIR%
docker-compose down