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
3. [Properties of project](#properties-of-project)
4. [Security](#security)
5. [Data Base](#data-base)
6. [Migration](#migration)
7. [Documentation](#documentation)
8. [Coverage obtained in the Tests](#coverage-obtained-in-the-tests)

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

## Properties of project
The application.properties file is located in the src/main/resources directory.
```
server.port=${es.mindata.challenge.port:8080}

# DataSource configuration
spring.datasource.driver-class-name=${DATA_SOURCE:org.h2.Driver}
spring.datasource.username=${DATA_SOURCE_USERNAME:admin}
spring.datasource.password=${DATA_SOURCE_PASSWORD:admin}
spring.datasource.url=${DATA_SOURCE_URL:jdbc:h2:mem:mindata}
spring.jpa.hibernate.ddl-auto=${DATA_SOURCE_HIBERNATE_DDL:validate}
spring.jpa.database-platform=${DATA_SOURCE_HIBERNATE_DIALECT:org.hibernate.dialect.H2Dialect}
spring.h2.console.enabled=${H2_CONSOLE_ENABLED:true}
spring.h2.console.settings.web-allow-others=true
```

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
H2 2.1.214 was used as database. If you use the administrator user, you can access the database:
[H2 Console](http://localhost:8080/h2-console/)

Remember that the JDBC URL to enter is:
```
jdbc:h2:mem:mindata
```

## Migration
Flyway 9.5.1 was used to perform the migration.
To use the application, the HERO table has been created and ten heroes have been inserted.

The migration files are located in the src/main/resources/db/migration directory.
```
V1__create_hero_table.sql
V2__import_hero_data.sql
```

## Documentation
OpenAPI was used to do the documentation: [OpenAPI](http://localhost:8080/swagger-ui/index.html#/)

![openapi](https://user-images.githubusercontent.com/86318023/224459967-553344b7-f81d-4fb6-b81e-019202322127.png)

## Coverage obtained in the Tests

- IDE Report:
<p align="center">
  <img src="https://user-images.githubusercontent.com/86318023/224450592-b37380fb-b557-4808-87ef-b39dcfeac984.png"/>
</p>

