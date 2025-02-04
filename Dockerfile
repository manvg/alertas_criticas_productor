FROM eclipse-temurin:22-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:22-jdk 

COPY --from=buildstage /app/target/alertas_criticas_productor-0.0.1-SNAPSHOT.jar /app/alertas_criticas_productor.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "/app/alertas_criticas_productor.jar" ]
