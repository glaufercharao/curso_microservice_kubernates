FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-student

COPY ./pom.xml /app

COPY ./msvc-student/.mvn ./.mvn

COPY ./msvc-student/mvnw .

COPY ./msvc-student/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-student/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc-student/target/msvc-student-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

CMD ["java", "-jar", "msvc-student-0.0.1-SNAPSHOT.jar"]