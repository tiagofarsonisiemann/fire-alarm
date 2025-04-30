# Use uma imagem oficial do Java 21
FROM eclipse-temurin:21-jdk-alpine

# Copie o JAR
ARG JAR_FILE=target/fire-alarm-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Substitua variáveis de ambiente no application.yml, se quiser (ver dica extra)


# Start da aplicação
ENTRYPOINT ["java","-jar","/app.jar"]