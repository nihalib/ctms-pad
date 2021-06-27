# Charging Tariff Management Service (CTMS)

CTMS handles registration for charging station and tariff information of each electric mobility service provider (eMSP).

# Installation

## Prerequisites

* [Java](https://openjdk.java.net/) v11
* [Maven](https://maven.apache.org/) v3.3
* [Docker](https://docs.docker.com/) v20.10.6
* [Postgresql](https://www.postgresql.org/) v9.6.21+

## Build
* To run maven build with junit test coverage
```bash
mvn clean install
```
* To run maven build along with `integration-test` mode
```bash
mvn clean install -P integration-test
```

## Run as standalone
##### Pre-requisites

- Make sure postgres is up & running.
```bash
docker run -p 5432:5432 -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ctms_pad --name postgres postgres:9.6.12
java -jar ctms-api/target/ctms-api.jar
```

## Run as docker container
```bash
docker network create ctms
docker run -p 5432:5432 -d -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ctms_pad --network=ctms --name postgres_ctms postgres:9.6.12
docker run -p 9900:9900 -d -e pg_host=postgres_ctms --network=ctms --name ctms bilal0606/ctms-api:2020.1.1
```

## Run on kubernetes
```bash
kubectl apply -f postgres-deployment.yaml,deployment.yaml
```
## Modules

- ctms-acceptance - Acceptance/Integration test suite for CTMS application. To know [more.](https://github.com/nihalib/ctms-pad/tree/main/ctms-acceptance#readme)
- ctms-api        - RESTful service implementation.
- ctms-model      - API definition for microservices.

## Swagger

```thymeleafurlexpressions
 http://localhost:9900/ctms-api/swagger-ui/index.html?configUrl=/ctms-api/v3/api-docs/swagger-config#/
```