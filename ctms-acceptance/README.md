# Charging Tariff Management Service - Acceptance Test

# Framework Overview
This module is intended for functional testing with the help of BDD testing framework. These test cases are written from
the view of user's criteria or from the view of the product owner. Using gherkins keywords such as Given, When, Then 
provided in the feature files can then be broken down into testable steps and bootstraps as test report during build
pipeline. All test runs once for every build and the results are written once.

This acceptance-test module comprises of -
* [Cucumber](https://cucumber.io/) v6.2.2
* [Wiremock](http://wiremock.org/) v2.27.2
* [Testcontainers](https://www.testcontainers.org/) v1.15.3

# Module Structure

```markdown
ctms-acceptance
    +--- src\test
    |    +-- java
    |    |   +-- steps    - Place cucumber step definition for given feature file
    |    |   +-- config   - Place the configuration such as wiremock stubs loader here 
    |    +-- resources
    |    |   +-- __files  - Place the wiremock stubs here
    |    |   +-- features - Place the cucumber feature file here
```
* Feature file      - It specifies the steps in BDD language style
* Step Definition   - Java class whereby the steps from the feature file are broken down to be coded into automation tests
* Stub              - A core feature of wiremock, the ability to return canned HTTP responses for requests matching criteria

# Test

## Prerequisites
Make sure docker is running inorder to fetch & run the instances of postgresql and redis

## Execute
```bash
mvn clean install -P integration-test
```

## Cucumber report
* Report is being generated in the form of HTML & Json in below path
```bash
ctms-acceptance/target/cucumber-reports/reports.html
```