# Charging Tariff Management Service (CTMS)

CTMS handles registration for charging station and tariff information of each electric mobility service provider (eMSP).

# Installation

## Prerequisites

* [Java](https://openjdk.java.net/) v11
* [Maven](https://maven.apache.org/) v3.3
* [Docker](https://docs.docker.com/) v20.10.6

## Build
* To run maven build with junit test coverage
```bash
mvn clean install
```
* To run maven build along with `integration-test` mode
```bash
mvn clean install -P integration-test
```

## Run

```bash
java -jar ctms-api/target/ctms-api.jar
```

## Modules

- ctms-acceptance - Acceptance/Integration test suite for CTMS application.
- ctms-api        - RESTful service implementation.
- ctms-model      - API definition for microservices.