@echo off
echo inicializando rabbit
call docker-compose up -d
call gradlew :challenge3-api:run
pause