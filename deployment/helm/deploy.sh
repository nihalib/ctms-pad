#!/bin/bash

# Check linux system information
uname -a

# application variables
APP_NAME=ctms
APP_NAMESPACE=${APP_NAME}

# verify kubernetes version
kubectl version --client

# create kubernetes namespace
#kubectl create namespace ${APP_NAMESPACE}
#kubectl get ns

# helm configuration
HELM_TIMEOUT=15m0s
HELM_VERSION=3.6.2

if [ "$1" = 'windows' ]; then
    curl -LO https://get.helm.sh/helm-v${HELM_VERSION}-windows-amd64.tar.gz \
    && tar xf helm-v${HELM_VERSION}-windows-amd64.tar.gz \
    && rm -f helm-v${HELM_VERSION}-windows-amd64.tar.gz \
    && mv windows-amd64/helm helm \
    && chmod +x helm \
    && rm -rf windows-amd64
elif [ "$1" = 'linux' ]; then
    curl -LO https://get.helm.sh/helm-v${HELM_VERSION}-linux-amd64.tar.gz \
    && tar xf helm-v${HELM_VERSION}-linux-amd64.tar.gz \
    && rm -f helm-v${HELM_VERSION}-linux-amd64.tar.gz \
    && mv linux-amd64/helm helm \
    && chmod +x helm \
    && rm -rf linux-amd64
fi

./helm version --client

./helm create ${APP_NAME} --namespace ${APP_NAMESPACE}

rm -f helm