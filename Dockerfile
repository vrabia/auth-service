FROM openjdk:17-alpine

WORKDIR /app
COPY .mvn ./.mvn
COPY mvnw  ./
COPY pom.xml ./
COPY src ./src

RUN ./mvnw clean install

CMD ["./mvnw", "spring-boot:run"]
