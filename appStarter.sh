#!/bin/bash

echo "Inicializando rabbit"
docker-compose up -d

echo "Aguardando a inicialização do RabbitMQ..."
while ! docker ps | grep -q "rabbitmq"; do
    sleep 1
done

echo "RabbitMQ está pronto. Iniciando a API..."
./gradlew :challenge3-api:run

read -p "Pressione Enter para sair..."
