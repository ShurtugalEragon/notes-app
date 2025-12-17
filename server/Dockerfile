FROM maven:3.9.11-eclipse-temurin-21 as build
WORKDIR /server
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src 
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre as production
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring:spring
WORKDIR /app
COPY --from=build --chown=spring:spring /server/target/server.jar server.jar 
EXPOSE 8080
CMD ["java", "-jar", "server.jar"]

