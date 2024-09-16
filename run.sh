#!/bin/bash

docker run -d  -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.5 start-dev

docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
