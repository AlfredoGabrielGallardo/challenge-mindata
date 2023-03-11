# REST API - MINDATA
The proyect was created as a challenge proposed by  _[Mindata](https://www.mindata.es/)_ company.

For the resolution, [Test Driven Development (TDD)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) was applied:

- Creation of the minimum necessary files.
- Creation of tests (unit and integration).
- Creation of implementations.
- Refactoring.

The latest LTS versions of Java, Spring and libraries were used.

## Table of Contents
1. [Technologies used](#technologies-used)
2. [Installation](#installation)
3. [Security](#security)
4. [Data Base](#data-base)
5. [Documentation](#documentation)
6. [Coverage obtained in the Tests](#coverage-obtained-in-the-tests)

## Technologies Used
* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [SpringBoot 3.0](https://spring.io/)
* [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
* [Spring Cache](https://spring.io/guides/gs/caching/)
* [JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
* [H2 Data Base](https://www.h2database.com/html/main.html)
* [Docker](https://www.docker.com/)
* [Flyway](https://flywaydb.org/)
* [Mockito](https://site.mockito.org/)
* [JUnit](https://junit.org/junit5/)

## Installation
To run the application, you must have Docker installed and the Docker daemon must be running. After that, follow the steps below:

1. Download the public project:
```
git clone https://github.com/AlfredoGabrielGallardo/challenge-mindata.git

```
2. In the folder where you downloaded the project (...\challenge-mindata), open the console and run the following command:
```
docker compose up

```
You already have the project up in port 8080!

## Security
Spring Security 6.0.2 was used to implement security in the application.

To use the application, you have two type of users:

- __Admin__: Full control of the application.
```
- username: admin
- password: admin
```

- __User__: This type can view the documentation, but is only authorized to make GET requests. It haven't permission to enter the database.
```
- username: user
- password: user
```

## Data Base
If you use the administrator user, you can access the database:
[H2 Console](http://localhost:8080/h2-console/)

## Documentation
OpenAPI was used to do the documentation: [OpenAPI](http://localhost:8080/swagger-ui/index.html#/)

## Coverage obtained in the Tests

- IDE Report:
<p align="center">
  <img src="https://user-images.githubusercontent.com/86318023/224450592-b37380fb-b557-4808-87ef-b39dcfeac984.png"/>
</p>

