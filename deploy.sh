#!/bin/bash

DOCKER_APP_NAME=heavenApp
DOCKER_DB_NAME=mydb
#DOCKER_NGINX_NAME=heavenNginx

EXIST_BLUE=$(docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml ps | grep Up)

EXIST_DB=$(docker-compose -p ${DOCKER_DB_NAME} -f docker-compose.db.yml ps | grep Up)

#EXIST_NGINX=$(docker-compose -p ${DOCKER_NGINX_NAME} -f docker-compose.db.yml ps | grep Up)


if [ -z "$EXIST_DB" ]; then
    echo "DB setting"
    docker-compose -p ${DOCKER_DB_NAME} -f docker-compose.db.yml up -d
fi

#if [ -z "$EXIST_NGINX" ]; then
#    echo "NGINX setting"
#    docker-compose -p ${DOCKER_NGINX_NAME} -f docker-compose.nginx.yml up -d
#fi

if [ -z "$EXIST_BLUE" ]; then
    echo "blue up"
    docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d

    sleep 10

    docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml down
else
    echo "green up"
    docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d

    sleep 10

    docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down
fi