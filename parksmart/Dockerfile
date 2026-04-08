FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", \
  "-Dspring.datasource.url=jdbc:mysql://maglev.proxy.rlwy.net:29697/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", \
  "-Dspring.datasource.username=root", \
  "-Dspring.datasource.password=dTJtttOumbbzoMEKZEQNZtnWgqgSLFZv", \
  "-jar", "app.jar"]