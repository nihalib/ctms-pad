#!/bin/sh
set -e

JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC \
-XX:MaxGCPauseMillis=200 \
-Djava.security.egd=file:/dev/./urandom"

#if [ "$1" = 'service' ]; then
#    exec java ${JAVA_OPTS} -jar "/app/bin/app.jar" ${APP_ARGS}
#fi

exec java ${JAVA_OPTS} -jar "/app/bin/app.jar" ${APP_ARGS}

exec "$@"