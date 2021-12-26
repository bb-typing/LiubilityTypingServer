#!/bin/bash
#构建昊方服务的docker shell

DOCKER_CONTAINER=liubility-typing-server
DOCKER_IMG=liubility-typing-server-image
SERVER_PORT=9999
APP_LOG=/log
HOST_LOG=/usr/local/src/liubility/typing/log

docker stop ${DOCKER_CONTAINER}
docker rm ${DOCKER_CONTAINER}
docker rmi ${DOCKER_IMG}
docker build -t ${DOCKER_IMG} .
#docker run --rm -it -p ${SERVER_PORT}:${SERVER_PORT} -v ${HOST_LOG}:${APP_LOG} --link ${MYSQL_CONTAINER}:${NETWORK} --name ${DOCKER_CONTAINER} ${DOCKER_IMG}
docker run --rm -it -d --tty -p ${SERVER_PORT}:${SERVER_PORT} -v ${HOST_LOG}:${APP_LOG} -v /root/.ssh:/root/.ssh -v /etc/hosts:/etc/hosts --name ${DOCKER_CONTAINER} ${DOCKER_IMG}


