#!/bin/bash

IMAGE_NAME="zaychik/system-user-rest"
CONTAINER_NAME="application"

docker container stop $CONTAINER_NAME
docker container rm $CONTAINER_NAME
docker run --name $CONTAINER_NAME -p 8888:8888 -d $IMAGE_NAME