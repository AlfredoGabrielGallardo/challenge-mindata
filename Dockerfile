FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17.0.1-jdk-slim
EXPOSE 8080

COPY --from=builder /usr/src/app/target/challenge-0.0.1-SNAPSHOT.jar /usr/app/challenge-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/usr/app/challenge-0.0.1-SNAPSHOT.jar"]